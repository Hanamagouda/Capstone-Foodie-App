/*
 * Author : Mushib Khan
 * Date : 30-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.controller;

import com.niit.domain.Customer;
import com.niit.exception.UserAlreadyExistsException;
import com.niit.exception.UserNotFoundException;
import com.niit.security.JwtSecurityTokenGenerator;
import com.niit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userAuth")
public class UserController {

    private JwtSecurityTokenGenerator jwtSecurityTokenGenerator;

    private UserService userService;

    @Autowired
    public UserController(JwtSecurityTokenGenerator jwtSecurityTokenGenerator, UserService userService) {
        this.jwtSecurityTokenGenerator = jwtSecurityTokenGenerator;
        this.userService = userService;
    }

    /**
     * This method is register the user.
     *
     * @param customer
     * @return User Details
     */

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody Customer customer) {
        try {
            Customer registeredCustomer = userService.addUser(customer);
            if (registeredCustomer != null) {
                return new ResponseEntity<Customer>(registeredCustomer, HttpStatus.OK);
            } else {
                throw new UserNotFoundException();
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to register new user", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method is to Login from user details
     *
     * @param customer
     * @return Login details
     */

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Customer customer) {
        try {
            Customer loggedCustomer = userService.loginUser(customer);
            if (loggedCustomer != null) {
                return new ResponseEntity<>(jwtSecurityTokenGenerator.tokenGenerator(loggedCustomer), HttpStatus.ACCEPTED);
            } else {
                throw new UserAlreadyExistsException();
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error occurred while trying to login through user", HttpStatus.BAD_REQUEST);
        }
    }
}
