package com.vaccinationdesk.vaccinationdeskservice.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.ListaEsperaRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class Distribuicao {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/";
    private static final String API_KEY = "AIzaSyDnusra6igG8TAkOY1CFFsuiyaMNEWyFLY";

    @Autowired
    private ListaEsperaRepository listaesperaRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    // private MQConsumer consumer;
    // ! ver como ir buscar a capacidade para o dia e atualiza-lo a cada dia que passa
    int capacidadeDia = 5;

    public Distribuicao() {
    }

    /**
     * 
     * Esta funcao faz um destribuicao/agendamento das vacinas pela ordem de marcacao,
     * podendo esta ser considerado o agendamento por defeito. 
     * @throws MessagingException - excecao de email
     * @throws IOException
     * @throws WriterException
     */
    public void distribuirVacinasPorOrdemMarcacao() throws MessagingException, WriterException, IOException {
        List<CentroVacinacao> centrosVacinacao = centroVacinacaoRepository.findAll();
        List<ListaEspera> listaEspera = listaesperaRepository.findAll(); //demora 5/6s a ler a base de
        int quantidadeDeCentros = centrosVacinacao.size();
        String moradasCentrosAPI = "";
        
        for (CentroVacinacao centro : centrosVacinacao) {
            moradasCentrosAPI += centro.getMorada() + "|";
        }
    
        for (int i = 0; i < capacidadeDia; i++) {
            ListaEspera pedido = listaEspera.get(i);
            listaesperaRepository.deleteListaEsperaByid(pedido.getId());

            //Utilização da Google API para escolher o centro de vacinação mais proximo do utente
            String resultadoAPIGoogle = getDistanceWithGoogleAPI(pedido.getUtente().getMorada(), moradasCentrosAPI);
            String centroEscolhido = calculateShorterPath(resultadoAPIGoogle, quantidadeDeCentros);

            for (CentroVacinacao centro : centrosVacinacao) {
                if (centroEscolhido.equals(centro.getMorada())) {
                    
                    //! por a data do sql com horas, minutos, e segundos
                    System.out.println("Data: " + pedido.getDataInscricao());
                    Timestamp data = pedido.getDataInscricao();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(data);
                    cal.add(Calendar.DAY_OF_WEEK, 14);
                    data.setTime(cal.getTime().getTime()); // or
                    data = new Timestamp(cal.getTime().getTime());
                    
                    System.out.println("Data da vacina: " + data);
                    
                    Agendamento agendamento = new Agendamento(pedido.getUtente(), data, centro);
                    agendamentoRepository.save(agendamento);
            
                    //! codigo do qr code
                    //String textToQRCode ="Nome - " + pedido.getUtente().getNome() + "\nNº Utente - " + pedido.getUtente().getID() + "\nCentro de Vacinação - "
                    //        + centroEscolhido + "\nData da Vacina - " + dataVacina.toString();
                    //generateQRCodeImage(textToQRCode, pedido.getUtente().getID());

                    //* Falei com o prof, não há problema em isto demorar 1.5/2 (s) a enviar o email                    
                    try {
                        sendEmail(pedido, data.toString(), centro);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    private static String getDistanceWithGoogleAPI(String from, String to) {
        // example of google api =
        // https://maps.googleapis.com/maps/api/distancematrix/json?origins=Viseu|Porto&destinations=Lisboa|Coimbra&key=AIzaSyDnusra6igG8TAkOY1CFFsuiyaMNEWyFLY
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url(BASE_URL + "json?origins=" + from + "&destinations=" + to + "&language=pt-PT&key=" + API_KEY)
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            System.err.println("ERROR: Google API - " + e.getMessage());
        }
        return null;
    }

    private static String calculateShorterPath(String responseString, int quantidadeCentros) {
        try {
            int indexLocation = 0;
            double minorDistance = 1000;

            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray rows = (JSONArray) jsonObject.get("rows");
            JSONObject firstRow = (JSONObject) rows.get(0);

            for (int i = 0; i < quantidadeCentros; i++) {
                JSONArray cities = (JSONArray) firstRow.get("elements");
                JSONObject city = (JSONObject) cities.get(i);
                JSONObject distanceAPI = (JSONObject) city.get("distance");
                String distanceString = (String) distanceAPI.get("text");
                distanceString = distanceString.replace("km", "").replace("m", "").replace(",", ".");
                double distance = Double.parseDouble(distanceString);
                if (distance < minorDistance) {
                    minorDistance = distance;
                    indexLocation = i;
                }
            }
            JSONArray locais = (JSONArray) jsonObject.get("destination_addresses");
            String cidade = (String) locais.get(indexLocation);
            cidade = cidade.replace(", Portugal", "");

            return cidade;
        } catch (Exception e) {
            System.err.println("ERROR: Calculating Shorter Path - " + e.getMessage());
        }
        return null;
    }
    
    public static void generateQRCodeImage(String text, int i)
	            throws WriterException, IOException {
	        QRCodeWriter qrCodeWriter = new QRCodeWriter();
	        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 400, 400);
            
	        Path path = FileSystems.getDefault().getPath("./src/main/resources/images/qr" + String.valueOf(i) + ".png");
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            
            //!nem com esta martelada isto funciona :(
            try {
                File f = new File("./src/main/resources/images/qr" + String.valueOf(i) + ".png");
                while (!f.exists()) {
                    Thread.sleep(1000);
                    System.out.println("adasdada");
                }
            }catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            } 
	    }
        
    void sendEmail(ListaEspera pedido, String dataVacina, CentroVacinacao centro)
            throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);// true = multipart message
        // helper.setTo(pedido.getUtente().getEmail());
        helper.setTo("joaosilveirasantos8@gmail.com"); // pass = joaosilveira8--
        String subject = "Agendamento da Vacina - " + pedido.getUtente().getNome() + " - Nº Utente - "
                + pedido.getUtente().getID();
        helper.setSubject(subject);
        helper.setText("A sua vacina encontra-se agendada para o dia " + dataVacina + " no "
                + centro.getNome() + " sendo a morada do mesmo: " + centro.getMorada()
                + "\nEm anexo segue-se um QR Code, que terá de ser apresentado à entrada do centro, na data estabelicida."
                + ".\n\n\n\nEsta é uma mensagem automática, por favor não responda a esta mensagem.");
        
        /*
        * POR MOTIVOS DO QRCODE AINDA NAO ESTAR A COM A RAPIDEZ DESEJADA, NAO VAI MANDAR QR CODE POR ENQ
        File f = new File("./src/main/resources/images/qr" + pedido.getUtente().getID() + ".png");
        while (true) {
            if (f.exists()) {
                helper.addInline("qrcode.png", new ClassPathResource("images/qr" + pedido.getUtente().getID() + ".png"));
                break;
            }
        }
        f.delete();
        */
        javaMailSender.send(msg);
    }
}
