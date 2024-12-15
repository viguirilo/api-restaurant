package com.restaurant.api.rest.v1.exception;

public class CityAlreadyExistsException extends MyEntityAlreadyExistsException {

    public CityAlreadyExistsException(String message) {
        super(message);
    }

}
