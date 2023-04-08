/*
 * Author : Mushib Khan
 * Date : 08-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.exception;

public class RestaurantAlreadyExistException extends Exception {

    public RestaurantAlreadyExistException() {
    }

    public RestaurantAlreadyExistException(String message) {
        super(message);
    }
}
