
/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

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


    @Autowired
    public RestaurantServiceImpl(RestaurantRepo restaurantRepo, RestaurantProxy restaurantProxy) {
        this.restaurantRepo = restaurantRepo;
        this.restaurantProxy = restaurantProxy;
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
    public Restaurant addCuisine(int restaurantId, Cuisine cuisine) throws CuisineAlreadyExistsException, RestaurantNotFoundException {
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
    public List<Cuisine> getAllCuisine(int restaurantId) throws CuisineNotFoundException, RestaurantNotFoundException {
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
    public List<Restaurant> restaurantSearchByLocation(String restaurantLocation) throws RestaurantNotFoundException {
        if (restaurantRepo.findByLocation(restaurantLocation).isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        List<Restaurant> byLocation = restaurantRepo.findByLocation(restaurantLocation);
        return byLocation;
    }

    @Override
    public Cuisine addCuisineToCart(String emailId, Cuisine cuisine) {
        ResponseEntity<?> responseEntity = restaurantProxy.addToCart(emailId, cuisine);
        return cuisine;
    }

    @Override
    public List<Cuisine> updateCuisine(int restaurantId, int cuisineId, Cuisine cuisine) throws RestaurantNotFoundException, CuisineNotFoundException {
        if (restaurantRepo.findById(restaurantId).isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        Restaurant restaurant = restaurantRepo.findById(restaurantId).get();
        if (restaurant.getCuisineList().iterator().next().getCuisineId() != cuisineId) {
            throw new CuisineNotFoundException();
        } else {
            restaurant.getCuisineList().iterator().next().setCuisineName(cuisine.getCuisineName());
            restaurant.getCuisineList().iterator().next().setCuisinePrice(cuisine.getCuisinePrice());
            restaurant.getCuisineList().iterator().next().setDescription(cuisine.getDescription());
            restaurant.getCuisineList().iterator().next().setCuisineImage(cuisine.getCuisineImage());
            restaurantRepo.save(restaurant);
        }
        return restaurant.getCuisineList();
    }

    @Override
    public Restaurant updateRestaurant(int restaurantId, Restaurant restaurant) throws RestaurantNotFoundException {
        if (restaurantRepo.findById(restaurantId).isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        Restaurant restaurantById = restaurantRepo.findById(restaurantId).get();
        restaurantById.setRestaurantName(restaurant.getRestaurantName());
        restaurantById.setRestaurantLocation(restaurant.getRestaurantLocation());
        restaurantById.setRestaurantImage(restaurant.getRestaurantImage());

        Restaurant save = restaurantRepo.save(restaurantById);
        return save;
    }

    @Override
    public List<Cuisine> deleteCuisine(int restaurantId, int cuisineId) throws RestaurantNotFoundException, CuisineNotFoundException {
        boolean found = false;
        if (restaurantRepo.findById(restaurantId).isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        Restaurant restaurant = restaurantRepo.findById(restaurantId).get();
        List<Cuisine> cuisineList = restaurant.getCuisineList();
        found = cuisineList.removeIf(cuisine -> cuisine.getCuisineId() == cuisineId);

        if (!found) {
            throw new CuisineNotFoundException();
        }
        restaurantRepo.save(restaurant);
        return restaurant.getCuisineList();
    }

    @Override
    public List<Restaurant> deleteRestaurant(int restaurantId) throws RestaurantNotFoundException {
        if (restaurantRepo.findById(restaurantId).isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        restaurantRepo.deleteById(restaurantId);
        return restaurantRepo.findAll();
    }

    @Override
    public Restaurant getRestaurantById(int restaurantId) throws RestaurantNotFoundException {
        if (restaurantRepo.findById(restaurantId).isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        Restaurant restaurant = restaurantRepo.findById(restaurantId).get();

        return restaurant;
    }
}
