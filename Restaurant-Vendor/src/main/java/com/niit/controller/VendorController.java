/*
 * Author : Mushib Khan
 * Date : 08-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.controller;

import com.niit.domain.Cuisine;
import com.niit.domain.Restaurant;
import com.niit.domain.Vendor;
import com.niit.exception.CuisineNotFoundException;
import com.niit.exception.RestaurantNotFoundException;
import com.niit.exception.VendorNotFoundException;
import com.niit.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {
    private VendorService vendorService;


    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/addVendor")
    public ResponseEntity<?> addVendor(@RequestBody Vendor vendor) {
        try {
            Vendor addedVendor = vendorService.addVendor(vendor);
            if (addedVendor == null) {
                throw new VendorNotFoundException();
            } else {
                return new ResponseEntity<Vendor>(addedVendor, HttpStatus.ACCEPTED);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to add new vendor", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addRestro/{vendorId}")
    public ResponseEntity<?> addRestaurant(@PathVariable String vendorId, @RequestBody Restaurant restaurant) {
        try {
            Vendor vendor = vendorService.addRestaurant(vendorId, restaurant);
            if (vendor == null) {
                throw new VendorNotFoundException();
            } else {
                return new ResponseEntity<Vendor>(vendor, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to save new Restaurant", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addCuisine/{vendorId}/{restaurantId}")
    public ResponseEntity<?> addCuisine(@PathVariable String vendorId, @PathVariable String restaurantId, @RequestBody Cuisine cuisine) {
        try {
            Vendor vendor = vendorService.addCuisine(vendorId, restaurantId, cuisine);
            if (vendor == null) {
                throw new VendorNotFoundException();
            } else {
                return new ResponseEntity<Vendor>(vendor, HttpStatus.ACCEPTED);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>("Error Occurred while trying to save new cuisine", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getRestro/{vendorId}")
    public ResponseEntity<?> getRestaurant(@PathVariable String vendorId) {
        try {
            Restaurant restaurant = vendorService.getRestaurant(vendorId);
            if (restaurant == null) {
                throw new RestaurantNotFoundException();
            } else {
                return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>("Error Occurred while trying to fetch restaurant by specific vendor", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cuisines/{vendorId}/{restaurantId}")
    public ResponseEntity<?> getAllCuisine(@PathVariable String vendorId, @PathVariable String restaurantId) {
        try {
            List<Cuisine> allCuisine = vendorService.getAllCuisine(vendorId, restaurantId);
            if (allCuisine == null) {
                throw new CuisineNotFoundException();
            } else {
                return new ResponseEntity<List<Cuisine>>(allCuisine, HttpStatus.ACCEPTED);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>("Error Occurred while trying to fetch all cuisine", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteCuisine/{vendorId}/{restaurantId}/{cuisineId}")
    public ResponseEntity<?> deleteCuisine(@PathVariable String vendorId, @PathVariable String restaurantId, @PathVariable int cuisineId) {
        try {
            List<Cuisine> cuisines = vendorService.deleteCuisine(vendorId, restaurantId, cuisineId);
            if (cuisines == null) {
                throw new CuisineNotFoundException();
            } else {
                return new ResponseEntity<List<Cuisine>>(cuisines, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>("Error Occurred while trying to delete specific cuisine", HttpStatus.BAD_REQUEST);
        }
    }
}
