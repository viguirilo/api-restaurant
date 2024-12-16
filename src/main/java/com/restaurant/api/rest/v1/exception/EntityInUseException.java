package com.restaurant.api.rest.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EntityInUseException extends RuntimeException {

    public EntityInUseException(String message) {
        super("The requested entity (" + message + ") is being used by one or more entities");
    }

}
