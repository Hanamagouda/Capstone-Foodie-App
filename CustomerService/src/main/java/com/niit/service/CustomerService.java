package com.niit.service;

import com.niit.domain.Customer;
import com.niit.domain.Restaurant;
import com.niit.exception.CustomerAlreadyExistsException;
import com.niit.exception.CustomerNotFoundException;
import com.niit.exception.RestaurantAlreadyExistsException;
import com.niit.exception.RestaurantNotFoundException;

import java.util.List;

public interface CustomerService {
    public Customer registerCustomer(Customer customer) throws CustomerAlreadyExistsException;

    public Customer addRestaurantToFavorite(String emailId, Restaurant restaurant) throws CustomerNotFoundException, RestaurantAlreadyExistsException;

    public Customer getCustomerById(String emailId) throws CustomerNotFoundException;

    public List<Restaurant> getFavoriteRestaurants(String emailId) throws CustomerNotFoundException;

    public Customer updateCustomer(String emailId, Customer customer) throws CustomerNotFoundException;

    public List<Restaurant> deleteRestaurantFromFavorite(String emailId, int restaurantId) throws RestaurantNotFoundException, CustomerNotFoundException;
}
