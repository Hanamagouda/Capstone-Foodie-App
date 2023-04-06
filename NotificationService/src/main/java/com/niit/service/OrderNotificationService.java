/*
 * Author : Mushib Khan
 * Date : 04-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.domain.OrderNotification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OrderNotificationService {
    private final JavaMailSender mailSender;
    private RabbitTemplate rabbitTemplate;

    public OrderNotificationService(JavaMailSender mailSender, RabbitTemplate rabbitTemplate) {
        this.mailSender = mailSender;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "order-placed")
    public void sendOrderConfirmation(OrderNotification order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("kaifansari668@gmail.com");
        message.setSubject("Order Confirmation");
        message.setText("Your order has been confirmed! Thank you for choosing our food delivery service.");
        mailSender.send(message);
    }


}
