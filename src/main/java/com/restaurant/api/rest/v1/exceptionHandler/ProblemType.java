package com.restaurant.api.rest.v1.exceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ProblemType {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "/bad-request", "Bad Request"),
    ENTITY_ALREADY_EXISTS(HttpStatus.CONFLICT, "/entity-already-exists", "Entity Already Exists"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND,"/entity-not-found", "Entity Not Found");

    private final HttpStatus status;
    private final String type;
    private final String title;

    ProblemType(HttpStatus status, String type, String title) {
        this.status = status;
        this.type = "http://restaurant.com.br" + type;
        this.title = title;
    }

}
