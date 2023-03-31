/*
 * Author : Mushib Khan
 * Date : 30-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.domain.Customer;
import com.niit.exception.UserAlreadyExistsException;
import com.niit.exception.UserNotFoundException;
import com.niit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method is to add user.
     *
     * @param customer - It will take user details as argument.
     * @return - It return user type.
     * @throws UserAlreadyExistsException - If any chance user already exists then it will throw exception.
     */
    @Override
    public Customer addUser(Customer customer) throws UserAlreadyExistsException {
        if (userRepository.findById(customer.getCustomerEmailId()).isPresent()) {
            throw new UserAlreadyExistsException();
        } else {
            return userRepository.save(customer);
        }
    }

    /**
     * This method is to login from User details.
     *
     * @param customer - It will take user details as argument.
     * @return - It return user type.
     * @throws UserNotFoundException - If any chance user not found then it will throw exception.
     */
    @Override
    public Customer loginUser(Customer customer) throws UserNotFoundException {
        if (userRepository.findById(customer.getCustomerEmailId()).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            Customer loginCustomer = userRepository.findById(customer.getCustomerEmailId()).get();
            if (loginCustomer.getPassword().equals(customer.getPassword())) {
                return loginCustomer;
            }
        }
        return null;
    }
}
