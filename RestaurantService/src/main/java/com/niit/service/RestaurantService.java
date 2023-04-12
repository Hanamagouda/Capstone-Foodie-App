/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.domain.Cuisine;
import com.niit.domain.Restaurant;
import com.niit.exception.CuisineAlreadyExistsException;
import com.niit.exception.CuisineNotFoundException;
import com.niit.exception.RestaurantAlreadyExistsException;
import com.niit.exception.RestaurantNotFoundException;

import java.util.List;

public interface RestaurantService {

    public Restaurant addRestaurant(Restaurant restaurant) throws RestaurantAlreadyExistsException;

    public List<Restaurant> getAllRestaurant() throws RestaurantNotFoundException;

    public Restaurant addCuisine(String restaurantId, Cuisine cuisine) throws CuisineAlreadyExistsException, RestaurantNotFoundException;

    public List<Cuisine> getAllCuisine(String restaurantId) throws CuisineNotFoundException, RestaurantNotFoundException;

    public Restaurant addRestaurantToFavorite(String emailId, Restaurant restaurant);

    public List<Restaurant> restaurantSearchByLocation(String restaurantLocation) throws RestaurantNotFoundException;

    public Cuisine addCuisineToOrder(int orderId, Cuisine cuisine);

    public List<Cuisine> updateCuisine(String restaurantId, int cuisineId, Cuisine ciusine) throws RestaurantNotFoundException, CuisineNotFoundException;

    public Restaurant updateRestaurant(String restaurantId, Restaurant restaurant) throws RestaurantNotFoundException;

    public List<Cuisine> deleteCuisine(String restaurantId, int cuisineId) throws RestaurantNotFoundException, CuisineNotFoundException;

    public List<Restaurant> deleteRestaurant(String restaurantId) throws RestaurantNotFoundException;
}
