/*
 * Author : Mushib Khan
 * Date : 31-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.controller;

import com.niit.domain.Customer;
import com.niit.domain.Restaurant;
import com.niit.exception.CustomerAlreadyExistsException;
import com.niit.exception.CustomerNotFoundException;
import com.niit.exception.RestaurantNotFoundException;
import com.niit.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        try {
            Customer registeredUser = customerService.registerCustomer(customer);
            if (registeredUser == null) {
                throw new CustomerAlreadyExistsException();
            } else {
                return new ResponseEntity<Customer>(registeredUser, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>("Error Occurred while registered new user", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addRestro/{emailId}")
    public ResponseEntity<?> addRestaurantToFavorite(@PathVariable String emailId, @RequestBody Restaurant restaurant) {
        try {
            Customer restaurantAdded = customerService.addRestaurantToFavorite(emailId, restaurant);
            if (restaurantAdded == null) {
                throw new CustomerNotFoundException();
            } else {
                return new ResponseEntity<Customer>(restaurantAdded, HttpStatus.ACCEPTED);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to add restaurant in favorite", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customerById/{emailId}")
    public ResponseEntity<?> getCustomerById(@PathVariable String emailId) {
        try {
            Customer customerById = customerService.getCustomerById(emailId);
            if (customerById == null) {
                throw new CustomerNotFoundException();
            } else {
                return new ResponseEntity<Customer>(customerById, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to fetch customer by id", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allRestro/{emailId}")
    public ResponseEntity<?> getFavoriteRestaurants(@PathVariable String emailId) {
        try {
            List<Restaurant> favoriteRestaurants = customerService.getFavoriteRestaurants(emailId);
            if (favoriteRestaurants == null) {
                throw new RestaurantNotFoundException();
            } else {
                return new ResponseEntity<List<Restaurant>>(favoriteRestaurants, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to fetch specific customer favorite restaurants", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteRestro/{emailId}/{restaurantId}")
    public ResponseEntity<?> deleteRestaurantFromFavorite(@PathVariable String emailId, @PathVariable String restaurantId) {
        try {
            List<Restaurant> restaurants = customerService.deleteRestaurantFromFavorite(emailId, restaurantId);
            if (restaurants == null) {
                throw new RestaurantNotFoundException();
            } else {
                return new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>("Error Occurred while trying to delete specific restaurant from favorite", HttpStatus.BAD_REQUEST);
        }
    }
}
