package com.vaccinationdesk.vaccinationdeskservice.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import com.google.zxing.WriterException;
import com.vaccinationdesk.vaccinationdeskservice.Service.Distribuicao;
import com.vaccinationdesk.vaccinationdeskservice.exception.ConflictException;
import com.vaccinationdesk.vaccinationdeskservice.exception.ResourceNotFoundException;
import com.vaccinationdesk.vaccinationdeskservice.model.Agendamento;
import com.vaccinationdesk.vaccinationdeskservice.model.CentroVacinacao;
import com.vaccinationdesk.vaccinationdeskservice.model.Doenca;
import com.vaccinationdesk.vaccinationdeskservice.model.DoencaPorUtente;
import com.vaccinationdesk.vaccinationdeskservice.model.ListaEspera;
import com.vaccinationdesk.vaccinationdeskservice.model.Lote;
import com.vaccinationdesk.vaccinationdeskservice.model.Utente;
import com.vaccinationdesk.vaccinationdeskservice.repository.AgendamentoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.CentroVacinacaoRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.DoencaPorUtenteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.DoencaRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.ListaEsperaRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.LoteRepository;
import com.vaccinationdesk.vaccinationdeskservice.repository.UtenteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = { "http://192.168.160.197:3000", "http://192.168.160.197:3000" })
public class VaccinationDeskController {
    
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private LoteRepository loteRepository;
    @Autowired
    private DoencaRepository doencaRepository;
    @Autowired
    private ListaEsperaRepository listaEsperaRepository;
    @Autowired
    private DoencaPorUtenteRepository dpuRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    @GetMapping("/lote")
    public List<Lote> getUtenteByNome() {
        Date d = new Date(System.currentTimeMillis());
        return loteRepository.findAllAfterDate(d);
    }

    @Async
    @PostMapping("/utente")
    public ResponseEntity<ListaEspera> createAppointment(@Valid @RequestBody Utente utente) throws ConflictException {

        if (utenteRepository.findUtenteById(utente.getID()) != null) {
            
            Utente utenteDB = utenteRepository.findUtenteById(utente.getID());
            if (!utente.getNome().equals(utenteDB.getNome()) || 
                !utente.getDataNascimento().toString().equals(utenteDB.getDataNascimento().toString())){
                throw new ConflictException("Dados inválidos");
            }
            List<Utente> findUtenteEmLE = listaEsperaRepository.findUtenteInListaEspera(utente);
            if (findUtenteEmLE!=null && findUtenteEmLE.size()!=0){
                throw new ConflictException("Utente com id "+utente.getID()+" já fez o pedido de agendamento");
            }
            long millis = System.currentTimeMillis();
            ListaEspera le = new ListaEspera(utenteDB, new Timestamp(millis));
            try {
                listaEsperaRepository.save(le);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(le);
        }

        return ResponseEntity.notFound().build();
    }

    @Async
    @PostMapping("/utente2")
    public ResponseEntity<String> createAppointment2(@Valid @RequestBody Utente utente) throws ConflictException, WriterException, IOException {

        utente.setMorada("Aveiro");
        System.out.println("MORADA");
        // utenteRepository.save(utente);
        // List<Utente> findUtenteEmLE = listaEsperaRepository.findUtenteInListaEspera(utente);
        // if (findUtenteEmLE!=null && findUtenteEmLE.size()!=0){
        //     throw new ConflictException("Utente com id "+utente.getID()+" já fez o pedido de agendamento");
        // }
        // long millis = System.currentTimeMillis();
        // ListaEspera le = new ListaEspera(utente, new Timestamp(millis));
        // try {
        //     listaEsperaRepository.save(le);
        // } catch (Exception e) {
        //     return ResponseEntity.badRequest().build();
        // }

        List<CentroVacinacao> centrosVacinacao = centroVacinacaoRepository.findAll();
        System.out.println("CENTROS");
        int quantidadeDeCentros = centrosVacinacao.size();
        String moradasCentrosAPI = "";

        // Gerar String com morada de todos os centros no formato certo para a Google API
        for (CentroVacinacao centro : centrosVacinacao) {
            if (centro.getMorada().equals("Porto")) {
                moradasCentrosAPI += centro.getMorada() + ",Portugal";
            }
            moradasCentrosAPI += centro.getMorada() + "|";
        }
        System.out.println("AFTER FOR");
        
            //listaesperaRepository.deleteListaEsperaByid(pedido.getId());

            String centroEscolhido = "Aveiro";

            for (CentroVacinacao centro : centrosVacinacao) {
                if (centroEscolhido.equals(centro.getMorada())) {
                    System.out.println("IFFFFFFFFFF");

                    // escolher a data em que o utente irá tomar a vacina
                    Date dataVacina = new Date(System.currentTimeMillis());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dataVacina);
                    cal.add(Calendar.DATE, 3);
                    dataVacina.setTime(cal.getTime().getTime());

                    // gerar QRcode e enviar email
                    String textToQRCode = "Nome - " + utente.getNome() + "\nN Utente - "
                            + utente.getID() + "\nCentro de Vacinacao - "
                            + 4 + "\nData da Vacina - " + dataVacina.toString();
                    Distribuicao.generateQRCodeImage(textToQRCode, utente.getID());
                    System.out.println("QR");
                    try {
                        sendEmail(utente.getID(), utente.getNome(), utente.getEmail(), dataVacina.toString(), centro);
                        System.out.println("CATCH");
                    } catch (Exception e) {
                        throw new ConflictException("Não foi possível enviar email." + e);
                    }
                    break;
                }
            }

        return ResponseEntity.ok("OK");

    }

    private String calculateShorterPath(String resultadoAPIGoogle, int quantidadeDeCentros) {
        return null;
    }

    @Async
    @PostMapping("/agendamento")
    public Agendamento getAgendamentoByUtente(@Valid @RequestBody(required = false) Utente utente) throws Exception{
        if ( utente !=null)
            try{
                if (utenteRepository.findUtenteById(utente.getID()) != null){
                    Utente utenteDB = utenteRepository.findUtenteById(utente.getID());
                    if (!utente.getNome().equals(utenteDB.getNome())){
                        throw new ConflictException("Dados inválidos");
                    }
                    if (!listaEsperaRepository.findUtenteInListaEspera(utente).isEmpty() && agendamentoRepository.findAllByUtente(utente.getID()) == null){
                        throw new ConflictException("Utente encontra-se em lista de espera. Aguarde pelo agendamento");
                    }
                }else{
                    throw new ResourceNotFoundException("Utente "+utente.getID()+" não encontrado!");
                }
            }catch(Exception e){
                throw e;
            }
        return agendamentoRepository.findAllByUtente(utente.getID());
    }

    @Async
    @GetMapping("/doencaPorUtente/{id}")
    public List<DoencaPorUtente> getDoencasPorUtente(@PathVariable Integer id) throws ResourceNotFoundException{
        try{
            Utente u = utenteRepository.findUtenteById(id);
            return dpuRepository.findByIdUtente(u);
        }catch(Exception e){
            throw new ResourceNotFoundException("Utente "+id+" não encontrado!");
        }
        
    }

    @Async
    @GetMapping("/doencas")
    public List<Doenca> doencas(){
        return doencaRepository.findAll();
    }

    @Async
    public
    void sendEmail(int n_utente, String nome_utente, String email, String dataVacina, CentroVacinacao centro)
            throws MessagingException, IOException, javax.mail.MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);// true = multipart message
        helper.setTo(email);
        // helper.setTo("joaosilveirasantos8@gmail.com"); // pass = joaosilveira8--
        String subject = "Agendamento da Vacina - " + nome_utente + " - Nº Utente - "
                + n_utente;
        helper.setSubject(subject);
        helper.setText("Exmo.(a) Senhor(a)\n\n" + nome_utente.toUpperCase() + "\nNº Utente: "
                + n_utente + "\n\nA sua vacina encontra-se agendada para o dia " + dataVacina + " no "
                + centro.getNome() + " sendo a morada do mesmo: " + centro.getMorada()
                + "\nEm anexo segue-se um QR Code, que terá de ser apresentado à entrada do centro, na data estabelecida."
                + "\n\nPode também consultar esta informação no site no nosso site, em Menu Inicial > Verificar Estado do Agendamento."
                + ".\n\n\n\nEsta é uma mensagem automática, por favor não responda. ");

        File f = new File("./src/main/resources/images/qr" + n_utente + ".png");
        ClassPathResource cp = new ClassPathResource("images/qr" + n_utente + ".png");
        while (true) {
            // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ClassPathResource.html#createRelative-java.lang.String-
            if (cp.exists() && cp.isReadable()) {
                helper.addInline("qr" + n_utente + ".png",
                        new ClassPathResource("images/qr" + n_utente + ".png"));
                break;
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        f.delete();
        javaMailSender.send(msg);
        System.out.println("Email enviado");
    }

}