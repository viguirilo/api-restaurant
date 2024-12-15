package com.restaurant.api.rest.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public abstract class MyEntityAlreadyExistsException extends RuntimeException {

    public MyEntityAlreadyExistsException(String message) {
        super(message);
    }

}
