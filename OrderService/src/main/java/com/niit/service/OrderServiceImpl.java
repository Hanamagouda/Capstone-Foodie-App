/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.domain.Cuisine;
import com.niit.domain.Order;
import com.niit.exception.CuisineAlreadyExistException;
import com.niit.exception.OrderAlreadyExistsException;
import com.niit.exception.OrderNotFoundException;
import com.niit.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order addOrder(Order order) throws OrderAlreadyExistsException {
        if (orderRepository.findById(order.getOrderId()).isPresent()) {
            throw new OrderAlreadyExistsException();
        }
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
