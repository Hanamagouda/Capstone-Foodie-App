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

    private final OrderNotificationRepo orderNotificationRepo;

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
        String emailId = (String) orderDTO.getJsonObject().get("emailId");
        System.out.println("EmailId : " + emailId);
        OrderNotification orderNotification = new OrderNotification();
        if (orderNotificationRepo.findById(emailId).isEmpty()) {
            orderNotification.setEmailId(emailId);
        }
        orderNotification.setMessage("Orders");
        orderNotification.setItemNames(orderDTO.getJsonObject());
        orderNotificationRepo.save(orderNotification);
    }


//        try{
//        String emailId = (String) orderDTO.getJsonObject().get("emailId");
//        System.out.println("EmailId " + emailId);
//        if(emailId.isEmpty()){
//            throw new IllegalArgumentException("EmailId is empty.");
//        }
//        if (orderNotificationRepo.findById(emailId).isPresent()) {
//            // Create a new OrderNotification object if it doesn't exist
//            OrderNotification orderNotification = new OrderNotification();
//            orderNotification.setEmailId(emailId);
//            orderNotification.setMessage("Orders");
//            orderNotification.setItemNames(orderDTO.getJsonObject());
//            orderNotificationRepo.save(orderNotification);
//        }else {
//            OrderNotification existingOrderNotification = orderNotificationRepo.findById(emailId).get();
//            existingOrderNotification.setMessage("Orders");
//            existingOrderNotification.setItemNames(orderDTO.getJsonObject());
//            orderNotificationRepo.save(existingOrderNotification);
//        }
//    }catch (Exception exception){
//        System.out.println(exception.getMessage());
//    }

}
