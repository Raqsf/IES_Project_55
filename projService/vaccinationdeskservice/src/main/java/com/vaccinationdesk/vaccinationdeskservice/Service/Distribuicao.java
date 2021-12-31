package com.vaccinationdesk.vaccinationdeskservice.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.ListaEsperaRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    // ! ver como ir buscar a capacidade para o dia
    int capacidadeDia = 20;

    public Distribuicao() {
        // MQConsumer consumer = new MQConsumer();
        // capacidadeDia = consumer.getQuantityForDay();
    }

    // ! aqui dividir de forma "igual" o numero de vacinas que ha, com o numero de
    // pessoas para determinado dia
    // ! e ainda fazer as contas, com o sitio de onde sao as pessoas e para que
    // centro de vacinacao deveriam ir
    // ! levar a vacina
    public void distribuirVacinasPorOrdemMarcacao() throws MessagingException {
        List<CentroVacinacao> centrosVacinacao = centroVacinacaoRepository.findAll();
        List<ListaEspera> listaEspera = listaesperaRepository.findAll(); //demora 5/6s a ler a base de
        int quantidadeDeCentros = centrosVacinacao.size();

        for (int i = 0; i < 5; i++) { // ! o for deverá iterar até ao máximo de vacinas que ha naquele dia
            ListaEspera pedido = listaEspera.get(i);
            listaesperaRepository.deleteListaEsperaByid(pedido.getId());

            String resultadoAPIGoogle = getDistanceWithGoogleAPI(pedido.getUtente().getMorada(), "Porto|Lisboa|Coimbra|Aveiro");
            String centroEscolhido = calculateShorterPath(resultadoAPIGoogle, quantidadeDeCentros);

            for (CentroVacinacao centro : centrosVacinacao) {
                if (centroEscolhido.equals(centro.getMorada())) {
                    Date dataVacina = Date.valueOf("2020-05-01");
                    Agendamento agendamento = new Agendamento(pedido.getUtente(), dataVacina, centro);
                    agendamentoRepository.save(agendamento);
                    //! ver se dá para executar este codigo de enviar emails com thread, pq demora 1.8 +/- segundos a enviar o email
                    //! e torna o processo de agendamento lento.
                    //? https://spring.io/guides/gs/async-method/ <- ver se isto resovle o problema
                   /*try {
                        sendEmail(pedido, "2020-05-01", centro);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
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
            JSONObject jsonobj = new JSONObject(responseString);
            JSONArray dist = (JSONArray) jsonobj.get("rows");
            JSONObject obj2 = (JSONObject) dist.get(0);
            int indexLocation = 0;
            double minorDistance = 1000;

            for (int i = 0; i < quantidadeCentros; i++) {
                JSONArray disting = (JSONArray) obj2.get("elements");
                JSONObject obj3 = (JSONObject) disting.get(i);
                JSONObject obj4 = (JSONObject) obj3.get("distance");
                String distance = (String) obj4.get("text");
                distance = distance.replace("km", "").replace("m", "").replace(",", ".");
                double distanceDouble = Double.parseDouble(distance);
                if (distanceDouble < minorDistance) {
                    minorDistance = distanceDouble;
                    indexLocation = i;
                }
            }

            JSONArray local = (JSONArray) jsonobj.get("destination_addresses");
            String cidade = (String) local.get(indexLocation);
            cidade = cidade.replace(", Portugal", "");

            return cidade;

        } catch (Exception e) {
            System.err.println("ERROR: Calculating shorter path - " + e.getMessage());
        }
        return null;
    }
        
    void sendEmail(ListaEspera pedido, String dataVacina, CentroVacinacao centro)
            throws MessagingException, IOException {
        double start, stop, delta;
        start = System.nanoTime();
                
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);// true = multipart message
        // helper.setTo(pedido.getUtente().getEmail());
        helper.setTo("joaosilveirasantos8@gmail.com"); // pass = joaosilveira8--
        String subject = "Agendamento da Vacina - " + pedido.getUtente().getNome() + " - Nº Utente - "
                + pedido.getUtente().getID();
        helper.setSubject(subject);
        helper.setText("A sua vacina encontra-se agendada para o dia " + dataVacina + " no "
                + centro.getNome() + " sendo a morada do mesmo: " + centro.getMorada()
                + ".\n\n\n\nEsta é uma mensagem automática, por favor não responda a esta mensagem.");
        // ? Código para enviar um ficheiro neste caso uma imagem (codigo qr) que seria
        // lido depois pelo raspberry p
        // FileSystemResource file = new FileSystemResource(new
        // File("classpath:android.png"));
        // Resource resource = new ClassPathResource("android.png");
        // InputStream input = resource.getInputStream();
        // ResourceUtils.getFile("classpath:android.png");
        javaMailSender.send(msg);
        stop = System.nanoTime();  // clock snapshot after
        delta = (stop - start) / 1e9; // convert nanoseconds to milliseconds
        System.out.println("send email: " + delta);
    }

}
