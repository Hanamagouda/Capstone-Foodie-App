/*
 * Author : Mushib Khan
 * Date : 04-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.config;


import com.niit.domain.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class OrderNotificationService {
    private final RabbitTemplate rabbitTemplate;

    public OrderNotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmailNotification(Order order) {
        Order orderSender = new Order();
        orderSender.setTo("kaifansari668@gmail.com");
        orderSender.setSubject("Order Confirmation - #" + order.getOrderId());
        orderSender.setBody("Thank you for your order. Your order has been confirmed. " +
                "Order details: " + order.toString());
        rabbitTemplate.convertAndSend("food_delivery_exchange", "notifications", orderSender);
    }
}
