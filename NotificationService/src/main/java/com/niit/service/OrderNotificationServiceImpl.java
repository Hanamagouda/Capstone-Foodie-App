/*
 * Author : Mushib Khan
 * Date : 11-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.config.OrderDTO;
import com.niit.domain.OrderNotification;
import com.niit.repo.OrderNotificationRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderNotificationServiceImpl implements OrderNotificationService {

    private OrderNotificationRepo orderNotificationRepo;

    @Autowired
    public OrderNotificationServiceImpl(OrderNotificationRepo orderNotificationRepo) {
        this.orderNotificationRepo = orderNotificationRepo;
    }

    @Override
    public OrderNotification getNotification(String emailId) {
        return orderNotificationRepo.findById(emailId).get();
    }

    @RabbitListener(queues = "order-queue")
    @Override
    public void saveNotification(OrderDTO orderDTO) {
        OrderNotification orderNotification = new OrderNotification();
        String emailId = (String) orderDTO.getJsonObject().get("emailId");
        if (orderNotificationRepo.findById(emailId).isEmpty()) {
            orderNotification.setEmailId(emailId);
        }
        orderNotification.setMessage("Orders");
        orderNotification.setItemNames(orderDTO.getJsonObject());
        orderNotificationRepo.save(orderNotification);
    }
}
