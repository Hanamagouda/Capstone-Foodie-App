/*
 * Author : Mushib Khan
 * Date : 30-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.domain.User;
import com.niit.exception.UserAlreadyExistsException;
import com.niit.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User addUser(User user) throws UserAlreadyExistsException {
        return null;
    }

    @Override
    public User loginUser(User user) throws UserNotFoundException {
        return null;
    }
}
