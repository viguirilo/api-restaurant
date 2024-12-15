package com.restaurant.api.rest.v1.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTITY_ALREADY_EXISTS("/entity-already-exists", "Entity already exists"),
    BAD_REQUEST("/bad-request", "Bad request"),
    ENTITY_NOT_FOUND("/entity-not-found", "Entity not found");

    private final String type;
    private final String title;

    ProblemType(String type, String title) {
        this.type = "http://restaurant.com.br" + type;
        this.title = title;
    }

}
