package br.rockethub.chessbackend.mail.configuration;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public  class ReceiverConfig {


    @Bean
    public Queue mailQueue() {
        return new Queue("mailQueue");
    }

    @Bean
    public Binding bindingRegistrationToken(DirectExchange direct, Queue mailQueue) {
        return BindingBuilder.bind(mailQueue).to(direct).with("mail");
    }
}
