/*
 * Author : Mushib Khan
 * Date : 30-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.domain.User;
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

    @Override
    public User addUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findById(user.getUserEmailId()).isPresent()) {
            throw new UserAlreadyExistsException();
        } else {
            return userRepository.save(user);
        }
    }

    @Override
    public User loginUser(User user) throws UserNotFoundException {
        if (userRepository.findById(user.getUserEmailId()).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            User loginUser = userRepository.findById(user.getUserEmailId()).get();
            if (loginUser.getUserPassword().equals(user.getUserPassword())) {
                return loginUser;
            }
        }
        return null;
    }
}
