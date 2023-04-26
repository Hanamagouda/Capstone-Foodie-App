/*
 * Author : Mushib Khan
 * Date : 11-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.proxy;

import com.niit.domain.Cuisine;
import com.niit.domain.Restaurant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-restaurant-service", url = "http://localhost:8084")
public interface RestaurantProxy {

    @PostMapping("/restaurant/addRestro")
    public ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant);

    @PostMapping("/restaurant/addCuisine/{restaurantId}")
    public ResponseEntity<?> addCuisine(@PathVariable int restaurantId, @RequestBody Cuisine cuisine);

    @PutMapping("/restaurant/updateCuisine/{restaurantId}/{cuisineId}")
    public ResponseEntity<?> updateCuisine(@PathVariable int restaurantId, @PathVariable int cuisineId, @RequestBody Cuisine cuisine);

    @PutMapping("/restaurant/updateRestro/{restaurantId}")
    public ResponseEntity<?> updateRestaurant(@PathVariable int restaurantId, @RequestBody Restaurant restaurant);

    @DeleteMapping("/restaurant/deleteCuisine/{restaurantId}/{cuisineId}")
    public ResponseEntity<?> deleteCuisine(@PathVariable int restaurantId, @PathVariable int cuisineId);

}
