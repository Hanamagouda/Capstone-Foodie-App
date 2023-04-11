/*
 * Author : Mushib Khan
 * Date : 11-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.controller;

import com.niit.service.OrderNotificationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/notification")
public class OrderNotificationController {
    private OrderNotificationService orderNotificationService;

    @Autowired
    public OrderNotificationController(OrderNotificationService orderNotificationService) {
        this.orderNotificationService = orderNotificationService;
    }

    @GetMapping("/getMessage")
    public ResponseEntity<?> notification(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String emailId = claims.getSubject();
        System.out.println("User emailId from claim(notification) :: " + claims.getSubject());
        System.out.println("emailId " + emailId);
        return new ResponseEntity<>(orderNotificationService.getNotification(emailId), HttpStatus.ACCEPTED);
    }
}
