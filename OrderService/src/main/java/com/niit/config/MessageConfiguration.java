/*
 * Author : Mushib Khan
 * Date : 04-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MessageConfiguration {
    private String exchangeName = "order-exchange";
    private String queueName = "order-queue";

    private JavaMailSender javaMailSender;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue registerQueue() {
        return new Queue(queueName);
    }

    @Bean
    public Jackson2JsonMessageConverter productJackson2JsonMessage() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(productJackson2JsonMessage());
        return rabbitTemplate;
    }

    @Bean
    public Binding bindingExchangeAndQueue(DirectExchange directExchange, Queue queue) {
        return BindingBuilder.bind(registerQueue()).to(directExchange).with("order-routing");
    }
}
