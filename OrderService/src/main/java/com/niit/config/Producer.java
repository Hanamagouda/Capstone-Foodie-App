/*
 *  Author : Hanamagouda Goudar
 *  Date : 04-04-2023
 *  Created with : IntelliJ IDEA Community Edition
 */

package com.niit.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange exchange;


    public void sendMessageToRabbitMq(OrderNotification orderNotification) {
        rabbitTemplate.convertAndSend(exchange.getName(), "order-routing", orderNotification);
    }
}
