/*
 * Author : Mushib Khan
 * Date : 04-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.config.OrderDTO;
import com.niit.domain.OrderNotification;
import com.niit.repository.OrderNotificationRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OrderNotificationServiceImpl implements OrderNotificationService {

    private OrderNotificationRepo orderNotificationRepo;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    public OrderNotificationServiceImpl(OrderNotificationRepo orderNotificationRepo, JavaMailSender javaMailSender) {
        this.orderNotificationRepo = orderNotificationRepo;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public OrderNotification orderDetails(OrderNotification orderNotification) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(orderNotification.getEmailId());
        simpleMailMessage.setText(orderNotification.getMessage());
        javaMailSender.send(simpleMailMessage);
        return orderNotificationRepo.save(orderNotification);
    }

    @RabbitListener(queues = "product-queue")
    @Override
    public void saveNotifications(OrderDTO orderDTO) {
        OrderNotification notification = new OrderNotification();
        String emailId = (String) orderDTO.getJsonObject().get("emailId");
        if (orderNotificationRepo.findById(emailId).isEmpty()) {
            notification.setEmailId(emailId);
        }
        notification.setMessage("Order Placed Successfully !!");
        notification.setOrderId(orderDTO.getJsonObject());
        orderNotificationRepo.save(notification);
    }
}
