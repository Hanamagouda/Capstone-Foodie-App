/*
 * Author : Mushib Khan
 * Date : 08-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.domain.Cuisine;
import com.niit.domain.Restaurant;
import com.niit.domain.Vendor;
import com.niit.exception.*;
import com.niit.repository.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    VendorRepo vendorRepo;

    @Autowired
    public VendorServiceImpl(VendorRepo vendorRepo) {
        this.vendorRepo = vendorRepo;
    }

    @Override
    public Vendor addVendor(Vendor vendor) throws VendorAlreadyExistException {
        if (vendorRepo.findById(vendor.getEmailId()).isPresent()) {
            throw new VendorAlreadyExistException();
        }
        return vendorRepo.save(vendor);
    }

    @Override
    public Vendor addRestaurant(String vendorId, Restaurant restaurant) throws VendorNotFoundException {
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }
        Vendor vendor = vendorRepo.findById(vendorId).get();
        if (vendor.getRestaurant() == null) {
            vendor.setRestaurant(restaurant);
        }
        Vendor save = vendorRepo.save(vendor);
        return save;
    }

    @Override
    public Vendor addCuisine(String vendorId, String restaurantId, Cuisine cuisine) throws RestaurantNotFoundException, CuisineAlreadyExistsException, VendorNotFoundException {
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }
        Vendor vendor = vendorRepo.findById(vendorId).get();
        if (vendor.getRestaurant().getRestaurantId().equals(restaurantId)) {
            if (vendor.getRestaurant().getCuisineList() == null) {
                vendor.getRestaurant().setCuisineList(Arrays.asList(cuisine));
            } else if (vendor.getRestaurant().getCuisineList().contains(cuisine)) {
                throw new CuisineAlreadyExistsException();
            } else {
                vendor.getRestaurant().getCuisineList().add(cuisine);
            }
        }
        return vendorRepo.save(vendor);
    }

    @Override
    public Restaurant getRestaurant(String vendorId) throws VendorNotFoundException, RestaurantNotFoundException {
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }
        Vendor vendor = vendorRepo.findById(vendorId).get();
        if (vendor.getRestaurant() == null) {
            throw new RestaurantNotFoundException();
        } else {
            return vendor.getRestaurant();
        }
    }

    @Override
    public List<Cuisine> getAllCuisine(String vendorId, String restaurantId) throws VendorNotFoundException, CuisineNotFoundException, RestaurantNotFoundException {
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }
        Vendor vendor = vendorRepo.findById(vendorId).get();
        if (vendor.getRestaurant().getRestaurantId().equals(restaurantId)) {
            if (vendor.getRestaurant().getCuisineList() == null) {
                throw new CuisineNotFoundException();
            } else {
                return vendor.getRestaurant().getCuisineList();
            }
        } else {
            throw new RestaurantNotFoundException();
        }
    }

    @Override
    public List<Cuisine> deleteCuisine(String vendorId, String restaurantId, int cuisineId) throws VendorNotFoundException, CuisineNotFoundException {
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }
        Vendor vendor = vendorRepo.findById(vendorId).get();
        Restaurant restaurant = vendor.getRestaurant();
        boolean found = false;

        for (Cuisine cuisine : restaurant.getCuisineList()) {
            if (cuisine.getCuisineId() == (cuisineId)) {
                restaurant.getCuisineList().remove(cuisine);
                vendorRepo.save(vendor);
            }
        }
        if (!found) {
            throw new CuisineNotFoundException();
        }
        return restaurant.getCuisineList();
    }


    @Override
    public Restaurant updateRestaurant(String vendorId, String restaurantId, Restaurant restaurant) throws VendorNotFoundException {
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }
        Vendor vendor = vendorRepo.findById(vendorId).get();
        if (vendor.getRestaurant().getRestaurantId().equals(restaurantId)) {
            vendor.getRestaurant().setRestaurantName(restaurant.getRestaurantName());
            vendor.getRestaurant().setRestaurantLocation(restaurant.getRestaurantLocation());
            vendor.getRestaurant().setRestaurantImage(restaurant.getRestaurantImage());
            vendorRepo.save(vendor);
        }
        return vendor.getRestaurant();
    }

    @Override
    public List<Cuisine> updateCuisine(String vendorId, String restaurantId, int cuisineId, Cuisine cuisine) throws VendorNotFoundException {
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }
        Vendor vendor = vendorRepo.findById(vendorId).get();
        if (vendor.getRestaurant().getRestaurantId().equals(restaurantId)) {
            int cuisineById = vendor.getRestaurant().getCuisineList().iterator().next().getCuisineId();
            if (cuisineById == cuisineId) {
                vendor.getRestaurant().getCuisineList().iterator().next().setCuisineName(cuisine.getCuisineName());
                vendor.getRestaurant().getCuisineList().iterator().next().setCuisineImage(cuisine.getCuisineImage());
                vendor.getRestaurant().getCuisineList().iterator().next().setCuisinePrice(cuisine.getCuisinePrice());
                vendor.getRestaurant().getCuisineList().iterator().next().setDescription(cuisine.getDescription());
                vendorRepo.save(vendor);
            }
        }
        return vendor.getRestaurant().getCuisineList();
    }

    @Override
    public List<Vendor> getAllVendors() throws VendorNotFoundException {
        if (vendorRepo.findAll().isEmpty()) {
            throw new VendorNotFoundException();
        }
        return vendorRepo.findAll();
    }

    @Override
    public Vendor getById(String vendorId) throws VendorNotFoundException {
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }
        Vendor vendor = vendorRepo.findById(vendorId).get();
        return vendor;
    }
}
