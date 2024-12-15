package com.restaurant.api.rest.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public abstract class MyBadRequestException extends RuntimeException {

    public MyBadRequestException(String message) {
        super(message);
    }

}
