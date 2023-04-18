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
    public ResponseEntity<?> addCuisine(@PathVariable int restaurantId, @RequestBody Cuisine cuisine) {
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
    public ResponseEntity<?> getAllCuisine(@PathVariable int restaurantId) {
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

    @GetMapping("/location/{restaurantLocation}")
    public ResponseEntity<?> searchByLocation(@PathVariable String restaurantLocation) {
        try {
            List<Restaurant> restaurants = restaurantService.restaurantSearchByLocation(restaurantLocation);
            if (restaurants == null) {
                throw new RestaurantNotFoundException();
            } else {
                return new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.ACCEPTED);
            }
        } catch (RestaurantNotFoundException exception) {
            return new ResponseEntity<String>("Error Occurred while trying to fetch restaurant by location", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addCuisineToOrder/{orderId}")
    public ResponseEntity<?> addCuisineToOrder(@PathVariable int orderId, @RequestBody Cuisine cuisine) throws CuisineNotFoundException {
        try {
            Cuisine cuisineToOrder = restaurantService.addCuisineToOrder(orderId, cuisine);
            if (cuisineToOrder == null) {
                throw new CuisineNotFoundException();
            } else {
                return new ResponseEntity<Cuisine>(cuisineToOrder, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to add cuisine to order", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateCuisine/{restaurantId}/{cuisineId}")
    public ResponseEntity<?> updateCuisine(@PathVariable int restaurantId, @PathVariable int cuisineId, @RequestBody Cuisine cuisine) {
        try {
            List<Cuisine> cuisines = restaurantService.updateCuisine(restaurantId, cuisineId, cuisine);
            if (cuisines == null) {
                throw new CuisineNotFoundException();
            } else {
                return new ResponseEntity<List<Cuisine>>(cuisines, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to update cuisine list", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateRestro/{restaurantId}")
    public ResponseEntity<?> updateRestaurant(@PathVariable int restaurantId, @RequestBody Restaurant restaurant) {
        try {
            Restaurant updatedRestro = restaurantService.updateRestaurant(restaurantId, restaurant);
            if (updatedRestro == null) {
                throw new RestaurantNotFoundException();
            } else {
                return new ResponseEntity<Restaurant>(updatedRestro, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>("Error Occurred while trying to update restaurant", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteCuisine/{restaurantId}/{cuisineId}")
    public ResponseEntity<?> deleteCuisine(@PathVariable int restaurantId, @PathVariable int cuisineId) throws RestaurantNotFoundException, CuisineNotFoundException {
//          try{
        List<Cuisine> cuisines = restaurantService.deleteCuisine(restaurantId, cuisineId);
        if (cuisines == null) {
            throw new CuisineNotFoundException();
        } else {
            return new ResponseEntity<List<Cuisine>>(cuisines, HttpStatus.OK);
        }
//        }catch (Exception exception){
//            return new ResponseEntity<String >("Error Occurred while trying to delete cuisine",HttpStatus.BAD_REQUEST);
//        }
    }

    @DeleteMapping("/deleteRestaurant/{restaurantId}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable int restaurantId) {
        try {
            List<Restaurant> restaurants = restaurantService.deleteRestaurant(restaurantId);
            if (restaurants == null) {
                throw new RestaurantNotFoundException();
            } else {
                return new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to delete restaurant", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getRestaurant/{restaurantId}")
    public ResponseEntity<?> getRestaurantById(@PathVariable int restaurantId) {
        try {
            Restaurant restaurantById = restaurantService.getRestaurantById(restaurantId);
            if (restaurantById == null) {
                throw new RestaurantNotFoundException();
            } else {
                return new ResponseEntity<Restaurant>(restaurantById, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to get restaurant", HttpStatus.BAD_REQUEST);
        }
    }
}
