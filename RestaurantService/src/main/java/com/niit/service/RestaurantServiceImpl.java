
/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.Proxy.OrderProxy;
import com.niit.Proxy.RestaurantProxy;
import com.niit.domain.Cuisine;
import com.niit.domain.Restaurant;
import com.niit.exception.CuisineAlreadyExistsException;
import com.niit.exception.CuisineNotFoundException;
import com.niit.exception.RestaurantAlreadyExistsException;
import com.niit.exception.RestaurantNotFoundException;
import com.niit.repository.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepo restaurantRepo;

    private RestaurantProxy restaurantProxy;
    private OrderProxy orderProxy;


    @Autowired
    public RestaurantServiceImpl(RestaurantRepo restaurantRepo, RestaurantProxy restaurantProxy, OrderProxy orderProxy) {
        this.restaurantRepo = restaurantRepo;
        this.restaurantProxy = restaurantProxy;
        this.orderProxy = orderProxy;
    }

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) throws RestaurantAlreadyExistsException {
        if (restaurantRepo.findById(restaurant.getRestaurantId()).isPresent()) {
            throw new RestaurantAlreadyExistsException();
        }
        return restaurantRepo.save(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() throws RestaurantNotFoundException {
        List<Restaurant> allRestro = restaurantRepo.findAll();
        if (allRestro.isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        return allRestro;
    }

    @Override
    public Restaurant addCuisine(String restaurantId, Cuisine cuisine) throws CuisineAlreadyExistsException, RestaurantNotFoundException {
        if (restaurantRepo.findById(restaurantId).isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        Restaurant restaurantById = restaurantRepo.findById(restaurantId).get();
        if (restaurantById.getCuisineList() == null) {
            restaurantById.setCuisineList(Arrays.asList(cuisine));
        } else if (restaurantById.getCuisineList().contains(cuisine)) {
            throw new CuisineAlreadyExistsException();
        } else {
            restaurantById.getCuisineList().add(cuisine);
        }
        return restaurantRepo.save(restaurantById);
    }

    @Override
    public List<Cuisine> getAllCuisine(String restaurantId) throws CuisineNotFoundException, RestaurantNotFoundException {
        if (restaurantRepo.findById(restaurantId).isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        Restaurant restaurant = restaurantRepo.findById(restaurantId).get();
        if (restaurant.getCuisineList().isEmpty()) {
            throw new CuisineNotFoundException();
        }
        return restaurant.getCuisineList();
    }

    @Override
    public Restaurant addRestaurantToFavorite(String emailId, Restaurant restaurant) {
        ResponseEntity<?> responseEntity = restaurantProxy.addRestaurantToFavorite(emailId, restaurant);
        return restaurant;
    }

    @Override
    public List<Restaurant> restaurantSearchByLocation(String restaurantLocation) throws RestaurantNotFoundException {
        if (restaurantRepo.findByLocation(restaurantLocation).isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        return restaurantRepo.findByLocation(restaurantLocation);
    }

    @Override
    public Cuisine addCuisineToOrder(String orderId, Cuisine cuisine) {
        ResponseEntity<?> responseEntity = orderProxy.saveCuisineToOrder(orderId, cuisine);
        return cuisine;
    }
}
