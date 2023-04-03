/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.controller;

import com.niit.domain.Restaurant;
import com.niit.exception.RestaurantAlreadyExistsException;
import com.niit.exception.RestaurantNotFoundException;
import com.niit.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/addRestro")
    public ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant) {
        try {
            Restaurant savedRestaurant = restaurantService.addRestaurant(restaurant);
            if (savedRestaurant == null) {
                throw new RestaurantAlreadyExistsException();
            } else {
                return new ResponseEntity<Restaurant>(savedRestaurant, HttpStatus.ACCEPTED);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while add new Restaurant", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allRestro")
    public ResponseEntity<?> getAllRestaurants() {
        try {
            List<Restaurant> allRestaurant = restaurantService.getAllRestaurant();
            if (allRestaurant == null) {
                throw new RestaurantNotFoundException();
            } else {
                return new ResponseEntity<>(allRestaurant, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>("Error Occurred while trying to fetch all restaurants", HttpStatus.BAD_REQUEST);
        }
    }
}
