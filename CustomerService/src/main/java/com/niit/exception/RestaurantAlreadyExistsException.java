/*
 * Author : Mushib Khan
 * Date : 31-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.ALREADY_REPORTED, reason = "Already Exists")
public class RestaurantAlreadyExistsException extends Exception {
    public RestaurantAlreadyExistsException() {
    }

    public RestaurantAlreadyExistsException(String message) {
        super(message);
    }
}
