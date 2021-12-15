package com.vaccinationdesk.vaccinationdeskservice.broker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import org.json.JSONObject;

public class Consumer {

    private Connection connection;
    private Channel channel;

    private List<String> peopleGettingVaccinatedList = new ArrayList<String>();
    private List<String> scheduleVaccineList = new ArrayList<String>();
    private List<String> vaccinesQuantityList = new ArrayList<String>();

    public Consumer() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        try {
            channel = connection.createChannel();
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        // consumeQueue();
    }

    public void consumeQueue() {
        try {
            channel.exchangeDeclare("logs", "fanout");
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        String queueName = "";
        try {
            queueName = channel.queueDeclare().getQueue();
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        try {
            channel.queueBind(queueName, "logs", "");
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        System.out.println("[*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            JSONObject json = new JSONObject(message);

            System.out.println("[x] Received '" + json.getString("type") + "'");

            // String uniqueID = UUID.randomUUID().toString(); //para gerear um id random

            switch (json.getString("type")) {
                
                //depois em cada case guardar nas listas...
                case "people_getting_vaccinated":
                    String quantity = json.getString("quantity");
                    break;
                case "schedule_vaccine":
                    String utente = json.getString("utente");
                    break;
                case "vaccines_quantity":
                    String utente_ = json.getString("utente");
                    break;
                default:
                    break;
            }

        };

        try {
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

    }

}
