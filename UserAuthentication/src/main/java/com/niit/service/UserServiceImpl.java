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

    /**
     * This method is to add user.
     *
     * @param user - It will take user details as argument.
     * @return - It return user type.
     * @throws UserAlreadyExistsException - If any chance user already exists then it will throw exception.
     */
    @Override
    public User addUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findById(user.getEmailId()).isPresent()) {
            throw new UserAlreadyExistsException();
        } else {
            return userRepository.save(user);
        }
    }

    /**
     * This method is to login from User details.
     *
     * @param user - It will take user details as argument.
     * @return - It return user type.
     * @throws UserNotFoundException - If any chance user not found then it will throw exception.
     */
    @Override
    public User loginUser(User user) throws UserNotFoundException {
        if (userRepository.findById(user.getEmailId()).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            User loginUser = userRepository.findById(user.getEmailId()).get();
            if (loginUser.getPassword().equals(user.getPassword())) {
                return loginUser;
            }
        }
        return null;
    }
}
