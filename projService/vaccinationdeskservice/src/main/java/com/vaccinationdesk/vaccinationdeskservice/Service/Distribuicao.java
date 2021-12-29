package com.vaccinationdesk.vaccinationdeskservice.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Distribuicao {

    @Autowired
    private ListaEsperaRepository listaesperaRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private CentroVacinacaoRepository centroVacinacaoRepository;

    @Autowired
    private JavaMailSender javaMailSender;
    
    //private MQConsumer consumer;
    //! ver como ir buscar a capacidade para o dia
    int capacidadeDia = 20;

    public Distribuicao() {
        //MQConsumer consumer = new MQConsumer();
        //capacidadeDia = consumer.getQuantityForDay();
    }

    //! aqui dividir de forma "igual" o numero de vacinas que ha, com o numero de pessoas para determinado dia
    //! e ainda fazer as contas, com o sitio de onde sao as pessoas e para que centro de vacinacao deveriam ir
    //! levar a vacina
    public void distribuirVacinasPorOrdemMarcacao() throws MessagingException {
        List<CentroVacinacao> centrosVacinacao = centroVacinacaoRepository.findAll();

        List<ListaEspera> listaEspera = listaesperaRepository.findAll();
        for (int i = 0; i < 5; i++) { //! o for deverá iterar até ao máximo de vacinas que ha naquele dia
            ListaEspera pedido = listaEspera.get(i);
            listaEspera.remove(pedido);

            //fazer o delete na base de dados da lista de espera
            listaesperaRepository.deleteListaEsperaByid(pedido.getId());

            String morada = pedido.getUtente().getMorada();
            //! ver a melhor maneira de escolher o centro de acordo com a morada da pessoa
            //! falar com a raquel sobre a tal API da google, i guess, que ela disse    
            for (CentroVacinacao centro : centrosVacinacao) {
                if (morada.equals(centro.getMorada())) {
                    Date dataVacina = Date.valueOf("2020-05-01");
                    Agendamento agendamento = new Agendamento(pedido.getUtente(), dataVacina, centro);
                    agendamentoRepository.save(agendamento);

                    try {
                        sendEmail(pedido, "2020-05-01", centro); 
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
    
    void sendEmail(ListaEspera pedido, String dataVacina, CentroVacinacao centro) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);// true = multipart message
        //helper.setTo(pedido.getUtente().getEmail());
        helper.setTo("joaosilveirasantos8@gmail.com"); //pass = joaosilveira8--
        String subject = "Agendamento da Vacina - " + pedido.getUtente().getNome()+ " - Nº Utente - " + pedido.getUtente().getID();
        helper.setSubject(subject);
        helper.setText("A sua vacina encontra-se agendada para o dia " + dataVacina + " no "
                + centro.getNome() + " sendo a morada do mesmo: " + centro.getMorada() + ".\n\n\n\nEsta é uma mensagem automática, por favor não responda a esta mensagem.");
        //? Código para enviar um ficheiro neste caso uma imagem (codigo qr) que seria lido depois pelo raspberry p
        //FileSystemResource file = new FileSystemResource(new File("classpath:android.png"));
        //Resource resource = new ClassPathResource("android.png");
        //InputStream input = resource.getInputStream();
        //ResourceUtils.getFile("classpath:android.png");        
        javaMailSender.send(msg);
    }
    
        


    


}
