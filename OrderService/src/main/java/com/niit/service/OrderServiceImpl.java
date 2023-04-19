/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.config.OrderDTO;
import com.niit.domain.Cuisine;
import com.niit.domain.Order;
import com.niit.exception.CuisineAlreadyExistException;
import com.niit.exception.OrderAlreadyExistsException;
import com.niit.exception.OrderNotFoundException;
import com.niit.repository.OrderRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
    private DirectExchange exchange;
    private RabbitTemplate template;

    private SequenceGeneratorService generatorService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, JavaMailSender javaMailSender, DirectExchange exchange, RabbitTemplate template, SequenceGeneratorService generatorService) {
        this.orderRepository = orderRepository;
        this.javaMailSender = javaMailSender;
        this.exchange = exchange;
        this.template = template;
        this.generatorService = generatorService;
    }

    @Override
    public Order addOrder(Order order, String emailId) throws OrderAlreadyExistsException {
        if (orderRepository.findById(order.getOrderId()).isPresent()) {
            throw new OrderAlreadyExistsException();
        }
        OrderDTO orderDTO = new OrderDTO();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId : ", order.getOrderId());
        jsonObject.put("emailId", emailId);
        jsonObject.put("quantity : ", order.getQuantity());
        jsonObject.put("Price : ", order.getPrice());
        jsonObject.put("Ordered Items : ", order.getOrderedItems());
        orderDTO.setJsonObject(jsonObject);
        template.convertAndSend(exchange.getName(), "order-routing", orderDTO);
        SimpleMailMessage message = new SimpleMailMessage();
        String newline = System.lineSeparator();
        message.setFrom(sender);
        message.setTo(emailId);
        message.setText("Order Placed Successfully : "
                + newline +
                "Email Id : " + order.getOrderId()
                + newline +
                "Items : " + order.getOrderedItems()
                + newline +
                "Quantity : " + order.getQuantity()
                + newline +
                "WelCome To Our Foodie Family :) ");
        message.setSubject("Foodie");
        javaMailSender.send(message);
        order.setOrderId(generatorService.getSequenceNumber(order.getSEQUENCE_NAME()));
        return orderRepository.save(order);
    }

    @Override
    public List<Order> deleteOrder(int orderId) throws OrderNotFoundException {
        if (orderRepository.findById(orderId).isEmpty()) {
            throw new OrderNotFoundException();
        }
        Order order = orderRepository.findById(orderId).get();
        orderRepository.delete(order);
        return orderRepository.findAll();
    }

    @Override
    public List<Cuisine> addCuisineToOrder(int orderId, Cuisine cuisine) throws OrderNotFoundException, CuisineAlreadyExistException {
        if (orderRepository.findById(orderId).isEmpty()) {
            throw new OrderNotFoundException();
        }
        Order order = orderRepository.findById(orderId).get();
        if (order.getOrderedItems().isEmpty()) {
            order.setOrderedItems(Arrays.asList(cuisine));
        } else if (order.getOrderedItems().contains(cuisine)) {
            throw new CuisineAlreadyExistException();
        } else {
            order.getOrderedItems().add(cuisine);
        }
        List<Cuisine> orderedItems = order.getOrderedItems();
        orderRepository.save(order);
        return orderedItems;
    }

    @Override
    public List<Order> getOrder() throws OrderNotFoundException {
        if (orderRepository.findAll().isEmpty()) {
            throw new OrderNotFoundException();
        }
        return orderRepository.findAll();
    }

    @Override
    public List<Cuisine> deleteFromOrder(int orderId, int cuisineId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId).get();
        if (order == null) {
            throw new OrderNotFoundException();
        }
        order.getOrderedItems().removeIf(cuisine -> cuisine.getCuisineId() == cuisineId);
        orderRepository.save(order);
        return order.getOrderedItems();
    }
}
