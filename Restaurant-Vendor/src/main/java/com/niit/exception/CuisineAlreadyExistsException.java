/*
 * Author : Mushib Khan
 * Date : 08-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.exception;

public class CuisineAlreadyExistsException extends Exception {

    public CuisineAlreadyExistsException() {
    }

    public CuisineAlreadyExistsException(String message) {
        super(message);
    }
}
