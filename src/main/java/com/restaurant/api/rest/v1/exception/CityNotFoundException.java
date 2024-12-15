package com.restaurant.api.rest.v1.exception;

public class CityNotFoundException extends MyEntityNotFoundException {

    public CityNotFoundException(String message) {
        super(message);
    }

}
