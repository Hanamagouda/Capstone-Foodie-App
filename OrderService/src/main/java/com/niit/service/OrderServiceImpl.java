/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.config.OrderDTO;
import com.niit.config.OrderNotification;
import com.niit.config.Producer;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    private final Producer producer;

    private JavaMailSender javaMailSender;

    private DirectExchange exchange;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, Producer producer, JavaMailSender javaMailSender, DirectExchange exchange, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.producer = producer;
        this.javaMailSender = javaMailSender;
        this.exchange = exchange;
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public Order addOrder(Order order) throws OrderAlreadyExistsException {
        if (orderRepository.findById(order.getOrderId()).isPresent()) {
            throw new OrderAlreadyExistsException();
        }
        OrderDTO orderDTO = new OrderDTO();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId", order.getOrderId());
        orderDTO.setJsonObject(jsonObject);

        rabbitTemplate.convertAndSend(exchange.getName(), "order-routing", orderDTO);
        OrderNotification details = new OrderNotification(order.getOrderId(), "Your Order has been placed SUCCESSFULLY", jsonObject);
        producer.sendMessageToRabbitMq(details);


        return orderRepository.save(order);
    }

    @Override
    public List<Order> deleteOrder(String orderId) throws OrderNotFoundException {
        if (orderRepository.findById(orderId).isEmpty()) {
            throw new OrderNotFoundException();
        }
        Order order = orderRepository.findById(orderId).get();
        orderRepository.delete(order);
        return orderRepository.findAll();
    }

    @Override
    public List<Cuisine> addCuisineToOrder(String orderId, Cuisine cuisine) throws OrderNotFoundException, CuisineAlreadyExistException {
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
    public List<Cuisine> deleteFromOrder(String orderId, int cuisineId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId).get();
        if (order == null) {
            throw new OrderNotFoundException();
        }
        order.getOrderedItems().removeIf(cuisine -> cuisine.getCuisineId() == cuisineId);
        orderRepository.save(order);
        List<Cuisine> orderedItems = order.getOrderedItems();
        return orderedItems;
    }
}
