package com.vaccinationdesk.vaccinationdeskservice.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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
import com.vaccinationdesk.vaccinationdeskservice.repository.CapacidadeRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.ListaEsperaRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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

    @Autowired
    private CapacidadeRepository capacidadeRepository;

    //int capacidadeDia = capacidadeRepository.getCapacidadePorDia(capacidadeRepository.getDiaDB().toString()) - 47;
    int capacidadeDia = 105;

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
    public void distribuirVacinasPorOrdemMarcacao() throws WriterException, IOException {
        List<CentroVacinacao> centrosVacinacao = centroVacinacaoRepository.findAll();
        List<ListaEspera> listaEspera = listaesperaRepository.findAll(); //demora 5/6s a ler a base de dados
        int quantidadeDeCentros = centrosVacinacao.size();
        String moradasCentrosAPI = "";

        for (CentroVacinacao centro : centrosVacinacao) {
            if (centro.getMorada().equals("Porto")) {
                moradasCentrosAPI += centro.getMorada() + ",Portugal";
            }
            moradasCentrosAPI += centro.getMorada() + "|";
        }
        
        for (int i = 0; i < capacidadeDia; i++) {
            ListaEspera pedido = listaEspera.get(i);
            listaesperaRepository.deleteListaEsperaByid(pedido.getId());
            //Utilização da Google API para escolher o centro de vacinação mais proximo do utente
            String resultadoAPIGoogle = getDistanceWithGoogleAPI(pedido.getUtente().getMorada() + ",Portugal", moradasCentrosAPI);
            String centroEscolhido = calculateShorterPath(resultadoAPIGoogle, quantidadeDeCentros);

            for (CentroVacinacao centro : centrosVacinacao) {
                if (centroEscolhido.equals(centro.getMorada())) {
                    //escolher a data em que o utente irá tomar a vacina                    
                    Timestamp dataVacina = pedido.getDataInscricao();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dataVacina);
                    cal.add(Calendar.DAY_OF_WEEK, 4);
                    dataVacina.setTime(cal.getTime().getTime());

                    Agendamento agendamento = new Agendamento(pedido.getUtente(), dataVacina, centro);
                    //System.out.println(agendamento.toString());
                    agendamentoRepository.save(agendamento);

                    //! codigo do qr code
                    String textToQRCode ="Nome - " + pedido.getUtente().getNome() + "\nNº Utente - " + pedido.getUtente().getID() + "\nCentro de Vacinação - "
                            + centroEscolhido + "\nData da Vacina - " + dataVacina.toString();
                    generateQRCodeImage(textToQRCode, pedido.getUtente().getID());
                    try {
                        sendEmail(pedido, dataVacina.toString(), centro);
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
    

    public void distribuirVacinasPorFiltros(String filtrosJSON) {
        List<CentroVacinacao> centrosVacinacao = centroVacinacaoRepository.findAll();
        List<ListaEspera> listaEspera;
        
        JSONObject jsonObject = new JSONObject(filtrosJSON);

        if (filtrosJSON.contains("idade") && filtrosJSON.contains("doenca")) {
            int idade = jsonObject.getInt("idade");
            int doenca = Integer.parseInt(jsonObject.getString("doenca")) + 1;
            listaEspera = listaesperaRepository.getListaEsperaByAgeAndDoenca(idade, doenca);
        } else if (filtrosJSON.contains("idade") && !filtrosJSON.contains("doenca")) {
            int idade = jsonObject.getInt("idade");
            listaEspera = listaesperaRepository.getListaEsperaByAge(idade);
        } else if (!filtrosJSON.contains("idade") && filtrosJSON.contains("doenca")) {
            int doenca = Integer.parseInt(jsonObject.getString("doenca")) + 1;
            listaEspera = listaesperaRepository.getListaEsperaByDoenca(doenca);
        } else {
            //! ver oq fazer com o else
            listaEspera = listaesperaRepository.findAll();
        }

        for (ListaEspera listaEspera2 : listaEspera) {
            System.out.println(listaEspera2);
        }



        //ver como o argumento de entrada como fazer a separacao dos filtros
        //listaEspera = listaesperaRepository.getListaEsperaByDoenca(2);
    
        int quantidadeDeCentros = centrosVacinacao.size();
        String moradasCentrosAPI = "";
        
        for (CentroVacinacao centro : centrosVacinacao) {
            if (centro.getMorada().equals("Porto")) {
                moradasCentrosAPI += centro.getMorada() + ",Portugal";
            }
            moradasCentrosAPI += centro.getMorada() + "|";
        }
        
        if (listaEspera.size() < capacidadeDia) {
            capacidadeDia = listaEspera.size();
        }

        for (int i = 0; i < capacidadeDia; i++) {
            ListaEspera pedido = listaEspera.get(i);
            listaesperaRepository.deleteListaEsperaByid(pedido.getId());

            //Utilização da Google API para escolher o centro de vacinação mais proximo do utente
            String resultadoAPIGoogle = getDistanceWithGoogleAPI(pedido.getUtente().getMorada() + ",Portugal", moradasCentrosAPI);
            String centroEscolhido = calculateShorterPath(resultadoAPIGoogle, quantidadeDeCentros);

            for (CentroVacinacao centro : centrosVacinacao) {
                if (centroEscolhido.equals(centro.getMorada())) {
                    //escolher a data em que o utente irá tomar a vacina                    
                    Timestamp dataVacina = pedido.getDataInscricao();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dataVacina);
                    cal.add(Calendar.DAY_OF_WEEK, 4);
                    dataVacina.setTime(cal.getTime().getTime());
                    
                    Agendamento agendamento = new Agendamento(pedido.getUtente(), dataVacina, centro);
                    agendamentoRepository.save(agendamento);
            
                    //! codigo do qr code
                    //String textToQRCode ="Nome - " + pedido.getUtente().getNome() + "\nNº Utente - " + pedido.getUtente().getID() + "\nCentro de Vacinação - "
                    //        + centroEscolhido + "\nData da Vacina - " + dataVacina.toString();
                    //generateQRCodeImage(textToQRCode, pedido.getUtente().getID());

                    try {
                        sendEmail(pedido, dataVacina.toString(), centro);
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
            System.out.println("path: " + path);
            
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
        helper.setText("Exmo.(a) Senhor(a)\n\n" + pedido.getUtente().getNome().toUpperCase() + "\nNº Utente: "+ pedido.getUtente().getID() + "\n\nA sua vacina encontra-se agendada para o dia " + dataVacina + " no "
                + centro.getNome() + " sendo a morada do mesmo: " + centro.getMorada()
                + "\nEm anexo segue-se um QR Code, que terá de ser apresentado à entrada do centro, na data estabelecida."
                + "\n\nPode também consultar esta informação no site no nosso site, em Menu Inicial > Verificar Estado do Agendamento."
                + ".\n\n\n\nEsta é uma mensagem automática, por favor não responda. ");
        
        
        // POR MOTIVOS DO QRCODE AINDA NAO ESTAR A COM A RAPIDEZ DESEJADA, NAO VAI MANDAR QR CODE POR ENQ
        File f = new File("./src/main/resources/images/qr" + pedido.getUtente().getID() + ".png");
        ClassPathResource cp = new ClassPathResource(
                        "images/qr" + pedido.getUtente().getID() + ".png");
        while (true) {
            //https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ClassPathResource.html#createRelative-java.lang.String-
            if (cp.exists() && cp.isReadable()) {
                System.out.println("PATH -->" + cp.getPath() + " exists: " + cp.exists() + " readable: " + cp.isReadable());
                helper.addInline("qr" + pedido.getUtente().getID() + ".png", new ClassPathResource("images/qr" + pedido.getUtente().getID() + ".png"));
                break;
            } else {
                try {
                    Thread.sleep(1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            } 

            }
        }
        //f.delete();
        
        javaMailSender.send(msg);
    }
}
