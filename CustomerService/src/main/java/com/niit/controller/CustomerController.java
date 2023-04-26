/*
 * Author : Mushib Khan
 * Date : 31-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.controller;

import com.niit.domain.Address;
import com.niit.domain.Cuisine;
import com.niit.domain.Customer;
import com.niit.domain.Restaurant;
import com.niit.exception.CuisineNotFoundException;
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
    public ResponseEntity<?> registerUser(@RequestBody Customer customer) throws CustomerAlreadyExistsException {
//        try {
        Customer registeredCustomer = customerService.registerCustomer(customer);
        if (registeredCustomer == null) {
            throw new CustomerAlreadyExistsException();
        } else {
            return new ResponseEntity<Customer>(registeredCustomer, HttpStatus.OK);
        }
//        } catch (Exception exception) {
//            return new ResponseEntity<String>("Error Occurred while trying to register new user", HttpStatus.BAD_REQUEST);
//        }
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
    public ResponseEntity<?> getCustomerById(@PathVariable String emailId) throws CustomerNotFoundException {
//        try {
        Customer customerById = customerService.getCustomerById(emailId);
        if (customerById == null) {
            throw new CustomerNotFoundException();
        } else {
            return new ResponseEntity<Customer>(customerById, HttpStatus.OK);
        }
//        } catch (Exception exception) {
//            return new ResponseEntity<String>("Error Occurred while trying to fetch customer by id", HttpStatus.BAD_REQUEST);
//        }
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
    public ResponseEntity<?> deleteRestaurantFromFavorite(@PathVariable String emailId, @PathVariable int restaurantId) throws RestaurantNotFoundException, CustomerNotFoundException {
        try {
            List<Restaurant> restaurants = customerService.deleteRestaurantFromFavorite(emailId, restaurantId);
            if (restaurants == null) {
                throw new CustomerNotFoundException();
            } else {
                return new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>("Error Occurred while trying to delete specific restaurant from favorite", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{emailId}")
    public ResponseEntity<?> updateCustomer(@PathVariable String emailId, @RequestBody Customer customer) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(emailId, customer);
            if (updatedCustomer == null) {
                throw new CustomerNotFoundException();
            } else {
                return new ResponseEntity<Customer>(updatedCustomer, HttpStatus.ACCEPTED);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to update customer details", HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/addAddress/{emailId}")
    public ResponseEntity<?> addAddress(@PathVariable String emailId, @RequestBody Address address) {
        try {
            List<Address> addresses = customerService.addAddress(emailId, address);
            if (addresses == null) {
                throw new CustomerNotFoundException();
            } else {
                return new ResponseEntity<List<Address>>(addresses, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to add customer address", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAddress/{emailId}")
    public ResponseEntity<?> getAddress(@PathVariable String emailId) {
        try {
            List<Address> address = customerService.getAddress(emailId);
            if (address == null) {
                throw new CustomerNotFoundException();
            } else {
                return new ResponseEntity<List<Address>>(address, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to get customer address", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cart/{emailId}")
    public ResponseEntity<?> addToCart(@PathVariable String emailId, @RequestBody Cuisine cuisine) {
        try {
            List<Cuisine> cuisines = customerService.addToCart(emailId, cuisine);
            if (cuisines == null) {
                throw new CuisineNotFoundException();
            } else {
                return new ResponseEntity<List<Cuisine>>(cuisines, HttpStatus.ACCEPTED);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to add cuisine into cart", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allCartItem/{emailId}")
    public ResponseEntity<?> getAllCart(@PathVariable String emailId) {
        try {
            List<Cuisine> cartItems = customerService.getCartItems(emailId);
            if (cartItems == null) {
                throw new CuisineNotFoundException();
            } else {
                return new ResponseEntity<List<Cuisine>>(cartItems, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to fetch specific user cart items", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removeFromCart/{emailId}")
    public ResponseEntity<?> removeFromCart(@PathVariable String emailId, @PathVariable int cuisineId) {
        try {
            List<Cuisine> cuisines = customerService.removeFromCart(emailId, cuisineId);
            if (cuisines == null) {
                throw new CuisineNotFoundException();
            } else {
                return new ResponseEntity<List<Cuisine>>(cuisines, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to remove specific cuisine from cart", HttpStatus.BAD_REQUEST);
        }
    }
}
