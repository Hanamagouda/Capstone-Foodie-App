package com.niit.service;

import com.niit.domain.Cuisine;
import com.niit.domain.Restaurant;
import com.niit.domain.Vendor;
import com.niit.exception.*;

import java.util.List;

public interface VendorService {

    public Vendor addVendor(Vendor vendor) throws VendorAlreadyExistException;

    public Vendor addRestaurant(String vendorId, Restaurant restaurant) throws VendorNotFoundException;

    public Vendor addCuisine(String vendorId, String restaurantId, Cuisine cuisine) throws RestaurantNotFoundException, CuisineAlreadyExistsException, VendorNotFoundException;

    public Restaurant getRestaurant(String vendorId) throws VendorNotFoundException, RestaurantNotFoundException;

    public List<Cuisine> getAllCuisine(String vendorId, String restaurantId) throws VendorNotFoundException, CuisineNotFoundException, RestaurantNotFoundException;


    public List<Cuisine> deleteCuisine(String vendorId, String restaurantId, int cuisineId) throws VendorNotFoundException, CuisineNotFoundException;

    public Restaurant updateRestaurant(String vendorId, String restaurantId, Restaurant restaurant) throws VendorNotFoundException;

    public List<Cuisine> updateCuisine(String vendorId, String restaurantId, int cuisineId, Cuisine cuisine) throws VendorNotFoundException;

    public List<Vendor> getAllVendors() throws VendorNotFoundException;

    public Vendor getById(String vendorId) throws VendorNotFoundException;
}
