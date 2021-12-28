/*package com.vaccinationdesk.vaccinationdeskservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@SpringBootApplication
public class Email implements CommandLineRunner {

    //https://docs.spring.io/spring/docs/5.1.6.RELEASE/spring-framework-reference/integration.html#mail
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void run(String... args) {
        System.out.println("Sending Email...");
        try {
            sendEmail(args[0]);
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
        System.out.println("Done");
    }

    void sendEmail(String email) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);// true = multipart message
        helper.setTo(email);
        helper.setSubject("Agendamento da Vacina");
        
        //default = text/plain
        //helper.setText("Check attachment for image!");

        //true = text/html
        helper.setText("<h1>Vacinação marcada para o <b>25/12/2021</b>!</h1>", true);

        //? Código para enviar um ficheiro
        //FileSystemResource file = new FileSystemResource(new File("classpath:android.png"));
        //Resource resource = new ClassPathResource("android.png");
        //InputStream input = resource.getInputStream();
        //ResourceUtils.getFile("classpath:android.png");
        
        javaMailSender.send(msg);
    }
}
*/