package com.restaurant.api.rest.v1.exception.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ProblemType {

    BAD_REQUEST(
            HttpStatus.BAD_REQUEST,
            Constants.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            Constants.BAD_REQUEST_DETAIL_MESSAGE
    ),
    ENTITY_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            Constants.ENTITY_ALREADY_EXISTS,
            HttpStatus.CONFLICT.getReasonPhrase(),
            Constants.CONFLICT_DETAIL_MESSAGE
    ),
    ENTITY_IN_USE(
            HttpStatus.CONFLICT,
            Constants.RESOURCE_IN_USE,
            HttpStatus.CONFLICT.getReasonPhrase(),
            Constants.CONFLICT_DETAIL_MESSAGE
    ),
    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            Constants.INTERNAL_SERVER_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            Constants.INTERNAL_SERVER_ERROR_DETAIL_MESSAGE
    ),
    AUTHORIZATION_DENIED(
            HttpStatus.UNAUTHORIZED,
            Constants.AUTHORIZATION_DENIED,
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            Constants.UNAUTHORIZED_DETAIL_MESSAGE
    ),
    CANNOT_CREATE_TRANSACTION(
            HttpStatus.SERVICE_UNAVAILABLE,
            Constants.CANNOT_CREATE_TRANSACTION,
            HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
            Constants.SERVICE_UNAVAILABLE_DETAIL_MESSAGE
    ),
    HTTP_MESSAGE_NOT_READABLE(
            HttpStatus.BAD_REQUEST,
            Constants.HTTP_MESSAGE_NOT_READABLE,
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            Constants.BAD_REQUEST_DETAIL_MESSAGE
    ),
    INVALID_FORMAT(
            HttpStatus.BAD_REQUEST,
            Constants.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            Constants.BAD_REQUEST_DETAIL_MESSAGE
    ),
    METHOD_ARGUMENT_TYPE_MISMATCH(
            HttpStatus.BAD_REQUEST,
            Constants.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            Constants.BAD_REQUEST_DETAIL_MESSAGE
    ),
    METHOD_ARGUMENT_NOT_VALID(
            HttpStatus.BAD_REQUEST,
            Constants.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            Constants.BAD_REQUEST_DETAIL_MESSAGE
    ),
    NO_RESOURCE_FOUND(
            HttpStatus.NOT_FOUND,
            Constants.RESOURCE_NOT_FOUND,
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            Constants.NOT_FOUND_DETAIL_MESSAGE
    ),
    PROPERTY_BIND_EXCEPTION(
            HttpStatus.BAD_REQUEST,
            Constants.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            Constants.BAD_REQUEST_DETAIL_MESSAGE
    );

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

    private static class Constants {
        public static final String BAD_REQUEST = "/bad-request";
        public static final String ENTITY_ALREADY_EXISTS = "/resource-already-exists";
        public static final String RESOURCE_IN_USE = "/resource-in-use";
        public static final String INTERNAL_SERVER_ERROR = "/internal-server-error";
        public static final String AUTHORIZATION_DENIED = "/authorization-denied";
        public static final String CANNOT_CREATE_TRANSACTION = "/cannot-create-transaction";
        public static final String HTTP_MESSAGE_NOT_READABLE = "/http-message-not-readable";
        public static final String RESOURCE_NOT_FOUND = "/resource-not-found";

        public static final String BAD_REQUEST_DETAIL_MESSAGE = "Something went wrong with your request. Check the syntax and try again";
        public static final String CONFLICT_DETAIL_MESSAGE = "The resource you are manipulating already exists or is in use for one or more resources";
        public static final String INTERNAL_SERVER_ERROR_DETAIL_MESSAGE = "Something unexpected happened. Try again later and, if the problem persists, be in touch with our support team";
        public static final String UNAUTHORIZED_DETAIL_MESSAGE = "You are not allowed to access this resource";
        public static final String SERVICE_UNAVAILABLE_DETAIL_MESSAGE = "The service is temporarily unavailable";
        public static final String NOT_FOUND_DETAIL_MESSAGE = "The resource you are looking for was not found";
    }

}
