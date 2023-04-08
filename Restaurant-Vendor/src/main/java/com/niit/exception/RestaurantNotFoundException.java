/*
 * Author : Mushib Khan
 * Date : 08-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.exception;

public class RestaurantNotFoundException extends Exception {
    public RestaurantNotFoundException() {
    }

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
