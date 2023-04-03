/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.controller;

import com.niit.domain.Cuisine;
import com.niit.domain.Restaurant;
import com.niit.exception.CuisineNotFoundException;
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
                return new ResponseEntity<List<Restaurant>>(allRestaurant, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to fetch all restaurants", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addCuisine/{restaurantId}")
    public ResponseEntity<?> addCuisine(@PathVariable String restaurantId, @RequestBody Cuisine cuisine) {
        try {
            Restaurant restaurant = restaurantService.addCuisine(restaurantId, cuisine);
            if (restaurant == null) {
                throw new RestaurantNotFoundException();
            } else {
                return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to add Cuisine in Restaurant", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allCuisine/{restaurantId}")
    public ResponseEntity<?> getAllCuisine(@PathVariable String restaurantId) {
        try {
            List<Cuisine> allCuisine = restaurantService.getAllCuisine(restaurantId);
            if (allCuisine == null) {
                throw new CuisineNotFoundException();
            } else {
                return new ResponseEntity<List<Cuisine>>(allCuisine, HttpStatus.ACCEPTED);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to fetch all Cuisine", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addRestroTofavorite/{emailId}")
    public ResponseEntity<?> addRestaurantToFavorite(@PathVariable String emailId, @RequestBody Restaurant restaurant) {
        try {
            Restaurant addRestaurantToFavorite = restaurantService.addRestaurantToFavorite(emailId, restaurant);
            if (addRestaurantToFavorite == null) {
                throw new RestaurantAlreadyExistsException();
            } else {
                return new ResponseEntity<>(addRestaurantToFavorite, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to add restaurant to specific customer favorite", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/byLocation/{restaurantLocation}")
    public ResponseEntity<?> searchByLocation(@PathVariable String restaurantLocation) {
        try {
            List<Restaurant> restaurants = restaurantService.searchByLocation(restaurantLocation);
            if (restaurants == null) {
                throw new RestaurantNotFoundException();
            } else {
                return new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.ACCEPTED);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to fetch restaurant by location", HttpStatus.BAD_REQUEST);
        }
    }
}
