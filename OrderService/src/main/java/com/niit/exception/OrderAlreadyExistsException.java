/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.ALREADY_REPORTED, reason = "Order Already Exist")
public class OrderAlreadyExistsException extends Exception {
    public OrderAlreadyExistsException() {
    }

    public OrderAlreadyExistsException(String message) {
        super(message);
    }
}
