/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Cuisine Not Found")
public class CuisineNotFoundException extends Exception {

    public CuisineNotFoundException() {
    }

    public CuisineNotFoundException(String message) {
        super(message);
    }
}
