/*
 * Author : Mushib Khan
 * Date : 31-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.domain.Customer;
import com.niit.domain.Restaurant;
import com.niit.exception.CustomerAlreadyExistsException;
import com.niit.exception.CustomerNotFoundException;
import com.niit.exception.RestaurantAlreadyExistsException;
import com.niit.exception.RestaurantNotFoundException;
import com.niit.proxy.CustomerProxy;
import com.niit.proxy.VendorProxy;
import com.niit.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepo customerRepo;

    private CustomerProxy customerProxy;
    private VendorProxy vendorProxy;

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo, CustomerProxy customerProxy, VendorProxy vendorProxy) {
        this.customerRepo = customerRepo;
        this.customerProxy = customerProxy;
        this.vendorProxy = vendorProxy;
    }

    @Override
    public Customer registerCustomer(Customer customer) throws CustomerAlreadyExistsException {
        if (customerRepo.findById(customer.getEmailId()).isPresent()) {
            throw new CustomerAlreadyExistsException();
        }
        Customer savedCustomer = customerRepo.save(customer);
        if (!(savedCustomer.getEmailId().isEmpty())) {
            ResponseEntity<?> responseEntity = customerProxy.saveCustomerToAuthentication(savedCustomer);
        }
        if (savedCustomer.getTypeOfUser().equalsIgnoreCase("vendor")) {
            ResponseEntity<?> responseEntity = vendorProxy.saveCustomerToVendor(savedCustomer);
        }
        return savedCustomer;
    }

    @Override
    public Customer addRestaurantToFavorite(String emailId, Restaurant restaurant) throws CustomerNotFoundException, RestaurantAlreadyExistsException {
        if (customerRepo.findById(emailId).isEmpty()) {
            throw new CustomerNotFoundException();
        }
        Customer customer = customerRepo.findById(emailId).get();
        if (customer.getFavorite() == null) {
            customer.setFavorite(Arrays.asList(restaurant));
        } else if (customer.getFavorite().contains(restaurant)) {
            throw new RestaurantAlreadyExistsException();
        } else {
            customer.getFavorite().removeIf(restro -> restro.getRestaurantId().equals(restaurant.getRestaurantId()));
            customer.getFavorite().add(restaurant);
        }
        return customerRepo.save(customer);
    }

    @Override
    public Customer getCustomerById(String emailId) throws CustomerNotFoundException {
        if (customerRepo.findById(emailId).isEmpty()) {
            throw new CustomerNotFoundException();
        }
        return customerRepo.findById(emailId).get();
    }

    @Override
    public List<Restaurant> getFavoriteRestaurants(String emailId) throws CustomerNotFoundException {
        if (customerRepo.findById(emailId).isEmpty()) {
            throw new CustomerNotFoundException();
        }
        List<Restaurant> favorite = customerRepo.findById(emailId).get().getFavorite();
        return favorite;
    }

    @Override
    public Customer updateCustomer(String emailId, Customer customer) throws CustomerNotFoundException {
        if (customerRepo.findById(emailId).isEmpty()) {
            throw new CustomerNotFoundException();
        }
        if (customerRepo.findById(emailId).isPresent()) {
            Customer newCustomer = customerRepo.findById(emailId).get();
            if (customer.getEmailId() != null) {
                newCustomer.setImage(customer.getImage());
                newCustomer.setName(customer.getName());
                newCustomer.setContactNumber(customer.getContactNumber());
                newCustomer.setAddress(customer.getAddress());
            }
            return customerRepo.save(newCustomer);
        }
        return null;
    }

    @Override
    public List<Restaurant> deleteRestaurantFromFavorite(String emailId, String restaurantId) throws RestaurantNotFoundException, CustomerNotFoundException {
        if (customerRepo.findById(emailId).isEmpty()) {
            throw new CustomerNotFoundException();
        }
        Customer customer = customerRepo.findById(emailId).get();
        boolean found = false;
        for (Restaurant restaurant : customer.getFavorite()) {
            if (restaurant.getRestaurantId().equals(restaurantId)) {
                customer.getFavorite().remove(restaurant);
                customerRepo.save(customer);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RestaurantNotFoundException();
        }
        return customer.getFavorite();
    }






}
