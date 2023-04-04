/*
 * Author : Mushib Khan
 * Date : 04-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.controller;

import com.niit.domain.OrderNotification;
import com.niit.service.OrderNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class OrderNotificationController {
    private OrderNotificationService orderNotificationService;


    @Autowired
    public OrderNotificationController(OrderNotificationService orderNotificationService) {
        this.orderNotificationService = orderNotificationService;
    }

    @PostMapping("/sendMail")
    public ResponseEntity<?> sendMail(@RequestBody OrderNotification orderNotification) {
        orderNotificationService.orderDetails(orderNotification);
        return new ResponseEntity<>("Mail Send Successfully", HttpStatus.OK);
    }
}
