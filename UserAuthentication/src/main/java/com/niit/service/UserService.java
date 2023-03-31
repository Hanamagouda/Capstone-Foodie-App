/*
 * Author : Mushib Khan
 * Date : 30-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.domain.Customer;
import com.niit.exception.UserAlreadyExistsException;
import com.niit.exception.UserNotFoundException;

public interface UserService {

    /**
     * This method is to add user.
     *
     * @param customer - It will take user details as argument.
     * @return - It return user type.
     * @throws UserAlreadyExistsException - If any chance user already exists then it will throw exception.
     */
    public Customer addUser(Customer customer) throws UserAlreadyExistsException;

    /**
     * This method is to log in from  details.
     *
     * @param customer - It will take user details as argument.
     * @return - It return user type.
     * @throws UserNotFoundException - If any chance user not found then it will throw exception.
     */

    public Customer loginUser(Customer customer) throws UserNotFoundException;
}
