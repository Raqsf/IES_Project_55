package com.vaccinationdesk.vaccinationdeskservice.broker;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

public class MQProducer {
    private Connection connection;
    private Channel channel;
    public static final String QUEUE = "qrcode_queue";

    public MQProducer() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("prod");
        factory.setPassword("prod");

        try {
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        try {
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public void createMessage(String message) throws IOException {
        channel.queueDeclare(QUEUE, false, false, false, null);

        try {
            channel.basicPublish("", QUEUE, null, message.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
