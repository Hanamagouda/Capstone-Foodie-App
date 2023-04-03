package com.niit.service;

import com.niit.domain.Cuisine;
import com.niit.domain.Order;
import com.niit.exception.CuisineAlreadyExistException;
import com.niit.exception.OrderAlreadyExistsException;
import com.niit.exception.OrderNotFoundException;

import java.util.List;

public interface OrderService {
    public Order addOrder(Order order) throws OrderAlreadyExistsException;

    public List<Order> deleteOrder(String orderId) throws OrderNotFoundException;

    public List<Cuisine> addCuisineToOrder(String orderId, Cuisine cuisine) throws OrderNotFoundException, CuisineAlreadyExistException;

    public List<Order> getOrder() throws OrderNotFoundException;

    public List<Cuisine> deleteFromOrder(String orderId, int cuisineId) throws OrderNotFoundException;

}
