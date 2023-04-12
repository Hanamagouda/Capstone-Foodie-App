package com.niit.service;

import com.niit.domain.Cuisine;
import com.niit.domain.Order;
import com.niit.exception.CuisineAlreadyExistException;
import com.niit.exception.OrderAlreadyExistsException;
import com.niit.exception.OrderNotFoundException;

import java.util.List;

public interface OrderService {
    public Order addOrder(Order order, String emailId) throws OrderAlreadyExistsException;

    public List<Order> deleteOrder(int orderId) throws OrderNotFoundException;

    public List<Cuisine> addCuisineToOrder(int orderId, Cuisine cuisine) throws OrderNotFoundException, CuisineAlreadyExistException;

    public List<Order> getOrder() throws OrderNotFoundException;

    public List<Cuisine> deleteFromOrder(int orderId, int cuisineId) throws OrderNotFoundException;

}
