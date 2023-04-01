/*
 * Author : Mushib Khan
 * Date : 30-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.controller;

import com.niit.domain.User;
import com.niit.exception.UserAlreadyExistsException;
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
     * @param user
     * @return User Details
     */

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            User registeredUser = userService.addUser(user);
            if (registeredUser == null) {
                throw new UserAlreadyExistsException();
            } else {
                return new ResponseEntity<User>(registeredUser, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to add new user", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method is to Login from user details
     *
     * @param user
     * @return Login details
     */

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            User loggedUser = userService.loginUser(user);
            if (loggedUser != null) {
                return new ResponseEntity<>(jwtSecurityTokenGenerator.tokenGenerator(loggedUser), HttpStatus.ACCEPTED);
            } else {
                throw new UserAlreadyExistsException();
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error occurred while trying to login through user", HttpStatus.BAD_REQUEST);
        }
    }
}
