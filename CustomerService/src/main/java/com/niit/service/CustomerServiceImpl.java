/*
 * Author : Mushib Khan
 * Date : 31-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.domain.Customer;
import com.niit.domain.Restaurant;
import com.niit.exception.CustomerAlreadyExistsException;
import com.niit.exception.CustomerNotFoundException;
import com.niit.exception.RestaurantAlreadyExistsException;
import com.niit.exception.RestaurantNotFoundException;
import com.niit.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerRepo customerRepo;


    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Customer registerCustomer(Customer customer) throws CustomerAlreadyExistsException {
        return null;
    }

    @Override
    public Customer addRestaurantToFavorite(String emailId, Restaurant restaurant) throws CustomerNotFoundException, RestaurantAlreadyExistsException {
        return null;
    }

    @Override
    public Customer getCustomerById(String emailId) throws CustomerNotFoundException {
        return null;
    }

    @Override
    public List<Restaurant> getFavoriteRestaurants(String emailId) throws CustomerNotFoundException {
        return null;
    }

    @Override
    public List<Restaurant> deleteRestaurantFromFavorite(String emailId, int restaurantId) throws RestaurantNotFoundException {
        return null;
    }
}
