package com.niit.service;

import com.niit.domain.Address;
import com.niit.domain.Cuisine;
import com.niit.domain.Customer;
import com.niit.domain.Restaurant;
import com.niit.exception.*;

import java.util.List;

public interface CustomerService {
    public Customer registerCustomer(Customer customer) throws CustomerAlreadyExistsException;

    public Customer addRestaurantToFavorite(String emailId, Restaurant restaurant) throws CustomerNotFoundException, RestaurantAlreadyExistsException;

    public Customer getCustomerById(String emailId) throws CustomerNotFoundException;

    public List<Restaurant> getFavoriteRestaurants(String emailId) throws CustomerNotFoundException;

    public Customer updateCustomer(String emailId, Customer customer) throws CustomerNotFoundException;

    public List<Restaurant> deleteRestaurantFromFavorite(String emailId, int restaurantId) throws RestaurantNotFoundException, CustomerNotFoundException;

    public List<Address> addAddress(String emailId, Address address) throws CustomerNotFoundException;

    public List<Address> getAddress(String emailId) throws CustomerNotFoundException;

    public List<Cuisine> addToCart(String emailId, Cuisine cuisine) throws CustomerNotFoundException;

    public List<Cuisine> getCartItems(String emailId) throws CustomerNotFoundException;

    public List<Cuisine> removeFromCart(String emailId, int cuisineId) throws CustomerNotFoundException, CuisineNotFoundException;

}
