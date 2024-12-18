package com.restaurant.api.rest.v1.exceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ProblemType {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "/bad-request", "Bad Request", "Something went wrong with your request. Please check that the entities involved exist and try again"),
    ENTITY_ALREADY_EXISTS(HttpStatus.CONFLICT, "/entity-already-exists", "Entity Already Exists", "The entity you are trying to create already exists"),
    ENTITY_IN_USE(HttpStatus.CONFLICT, "/entity-in-use", "Entity in Use", "The entity you are trying to remove or update is already in use for one or more entities"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "/internal-server-error", "Internal Server Error", "Something unexpected happened. Try again and if the problem persists, be in touch with our support team"),
    HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "/http-message-not-readable", "Bad Request", "Something went wrong with your request. Please, check the syntax and try again"),
    INVALID_FORMAT(HttpStatus.BAD_REQUEST, "/bad-request", "Bad Request", "Something went wrong with your request. Please, check the syntax and try again"),
    METHOD_ARGUMENT_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "/bad-request", "Bad Request", "Something went wrong with your request. Please, check the syntax and try again"),
    NO_RESOURCE_FOUND(HttpStatus.NOT_FOUND, "/no-resource-found", "No Resource Found", "The resource you are looking for was not found"),
    PROPERTY_BIND_EXCEPTION(HttpStatus.BAD_REQUEST, "/bad-request", "Bad Request", "Something went wrong with your request. Please, check the syntax and try again");

    private final HttpStatus status;
    private final String type;
    private final String title;
    private final String detail;

    ProblemType(HttpStatus status, String type, String title, String detail) {
        this.status = status;
        this.type = "http://restaurant.com.br" + type;
        this.title = title;
        this.detail = detail;
    }

}
