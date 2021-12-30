package com.vaccinationdesk.vaccinationdeskservice.broker;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    public static final String EXCHANGE_NAME = "";
    public static final String QUEUE = "vaccination_queue";
    public static final String ROUTING_KEY = "vaccination_queue";

    @Bean
	Queue queue() {
		return new Queue(QUEUE, true);
	}

    @Bean
	DirectExchange exchange() {
		return new DirectExchange(EXCHANGE_NAME);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}

    @Bean
    public MQConsumer receiver() {
        return new MQConsumer();
    }
}