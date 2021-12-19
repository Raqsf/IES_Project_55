package com.vaccinationdesk.vaccinationdeskservice.broker;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class MQConsumer {

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listen(String input) {
        
        System.out.println("   Receiver#receive input: " + input);
    }

}