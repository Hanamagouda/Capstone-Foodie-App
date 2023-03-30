/*
 * Author : Mushib Khan
 * Date : 30-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.ALREADY_REPORTED, reason = "Trying to add duplicate user")
public class UserAlreadyExists extends Exception {
    public UserAlreadyExists() {
    }

    public UserAlreadyExists(String message) {
        super(message);
    }
}
