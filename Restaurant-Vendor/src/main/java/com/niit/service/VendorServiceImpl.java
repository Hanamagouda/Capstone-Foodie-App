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
import com.niit.proxy.RestaurantProxy;
import com.niit.proxy.UserAuthProxy;
import com.niit.repository.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    VendorRepo vendorRepo;

    RestaurantProxy restaurantProxy;

    UserAuthProxy userAuthProxy;

    SequenceGeneratorService generatorService;

    @Autowired
    public VendorServiceImpl(VendorRepo vendorRepo, RestaurantProxy restaurantProxy, UserAuthProxy userAuthProxy, SequenceGeneratorService generatorService) {
        this.vendorRepo = vendorRepo;
        this.restaurantProxy = restaurantProxy;
        this.userAuthProxy = userAuthProxy;
        this.generatorService = generatorService;
    }

    @Override
    public Vendor addVendor(Vendor vendor) throws VendorAlreadyExistException {
        if (vendorRepo.findById(vendor.getEmailId()).isPresent()) {
            throw new VendorAlreadyExistException();
        }
        Vendor savedVendor = vendorRepo.save(vendor);
        if (!(savedVendor.getEmailId().isEmpty())) {
            userAuthProxy.saveVendorToAuthentication(savedVendor);
        }
        return savedVendor;
    }

    @Override
    public Vendor addRestaurant(String vendorId, Restaurant restaurant) throws VendorNotFoundException {
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }
        Vendor vendor = vendorRepo.findById(vendorId).get();
        if (vendor.getRestaurant() == null) {
            vendor.setRestaurant(restaurant);
            vendor.getRestaurant().setRestaurantId(generatorService.getSequenceNumber(vendor.getSEQUENCE_NAME()));
        }
        Vendor saved = vendorRepo.save(vendor);
        if (!(saved.getRestaurant() == null)) {
            restaurantProxy.addRestaurant(restaurant);
        }
        return saved;
    }

    @Override
    public Vendor addCuisine(String vendorId, int restaurantId, Cuisine cuisine) throws RestaurantNotFoundException, CuisineAlreadyExistsException, VendorNotFoundException {
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }

        Vendor vendor = vendorRepo.findById(vendorId).get();
        if (vendor.getRestaurant().getRestaurantId() == restaurantId) {
            if (vendor.getRestaurant().getCuisineList() == null) {
                vendor.getRestaurant().setCuisineList(Arrays.asList(cuisine));
            } else if (vendor.getRestaurant().getCuisineList().contains(cuisine)) {
                throw new CuisineAlreadyExistsException();
            } else {
                vendor.getRestaurant().getCuisineList().add(cuisine);
            }
        }
        cuisine.setCuisineId(generatorService.getSequenceNumber(vendor.getSEQUENCE_NAME()));
        Vendor saved = vendorRepo.save(vendor);
        if (!(vendor.getRestaurant().getCuisineList().isEmpty())) {
            restaurantProxy.addCuisine(saved.getRestaurant().getRestaurantId(), cuisine);
        }
        return saved;
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
    public List<Cuisine> getAllCuisine(String vendorId, int restaurantId) throws VendorNotFoundException, CuisineNotFoundException, RestaurantNotFoundException {
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }
        Vendor vendor = vendorRepo.findById(vendorId).get();
        if (vendor.getRestaurant().getRestaurantId() == restaurantId) {
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
    public List<Cuisine> deleteCuisine(String vendorId, int restaurantId, int cuisineId) throws VendorNotFoundException, CuisineNotFoundException {
        boolean found = false;
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }
        Vendor vendor = vendorRepo.findById(vendorId).get();
        Restaurant restaurant = vendor.getRestaurant();
        List<Cuisine> cuisineList = restaurant.getCuisineList();
        found = cuisineList.removeIf(cuisine -> cuisine.getCuisineId() == cuisineId);
        ResponseEntity<?> responseEntity = restaurantProxy.deleteCuisine(restaurantId, cuisineId);

        if (!found) {
            throw new CuisineNotFoundException();
        }
        vendorRepo.save(vendor);
        return restaurant.getCuisineList();

    }


    @Override
    public Restaurant updateRestaurant(String vendorId, int restaurantId, Restaurant restaurant) throws VendorNotFoundException {
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }
        Vendor vendor = vendorRepo.findById(vendorId).get();
        if (vendor.getRestaurant().getRestaurantId() == restaurantId) {
            vendor.getRestaurant().setRestaurantName(restaurant.getRestaurantName());
            vendor.getRestaurant().setRestaurantLocation(restaurant.getRestaurantLocation());
            vendor.getRestaurant().setRestaurantImage(restaurant.getRestaurantImage());
            vendorRepo.save(vendor);
        }
        restaurantProxy.updateRestaurant(restaurantId, restaurant);
        return vendor.getRestaurant();
    }

    @Override
    public List<Cuisine> updateCuisine(String vendorId, int restaurantId, int cuisineId, Cuisine cuisine) throws VendorNotFoundException {
        if (vendorRepo.findById(vendorId).isEmpty()) {
            throw new VendorNotFoundException();
        }
        Vendor vendor = vendorRepo.findById(vendorId).get();
        if (vendor.getRestaurant().getRestaurantId() == restaurantId) {
            int cuisineById = vendor.getRestaurant().getCuisineList().iterator().next().getCuisineId();
            if (cuisineById == cuisineId) {
                vendor.getRestaurant().getCuisineList().iterator().next().setCuisineName(cuisine.getCuisineName());
                vendor.getRestaurant().getCuisineList().iterator().next().setCuisineImage(cuisine.getCuisineImage());
                vendor.getRestaurant().getCuisineList().iterator().next().setCuisinePrice(cuisine.getCuisinePrice());
                vendor.getRestaurant().getCuisineList().iterator().next().setDescription(cuisine.getDescription());
                vendorRepo.save(vendor);
            }
        }
        restaurantProxy.updateCuisine(restaurantId, cuisineId, cuisine);
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
