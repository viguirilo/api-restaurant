package com.restaurant.api.rest.v1.exceptionHandler;

import com.restaurant.api.rest.v1.exception.BadRequestException;
import com.restaurant.api.rest.v1.exception.EntityAlreadyExistsException;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static com.restaurant.api.rest.v1.exceptionHandler.ProblemType.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ProblemDetail problemDetail = new ProblemDetail(
                BAD_REQUEST.getStatus().value(),
                BAD_REQUEST.getType(),
                BAD_REQUEST.getTitle(),
                BAD_REQUEST.getDetail(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), BAD_REQUEST.getStatus(), request);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<?> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex, WebRequest request) {
        ProblemDetail problemDetail = new ProblemDetail(
                ENTITY_ALREADY_EXISTS.getStatus().value(),
                ENTITY_ALREADY_EXISTS.getType(),
                ENTITY_ALREADY_EXISTS.getTitle(),
                ENTITY_ALREADY_EXISTS.getDetail(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), ENTITY_ALREADY_EXISTS.getStatus(), request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request) {
        ProblemDetail problemDetail = new ProblemDetail(
                ENTITY_IN_USE.getStatus().value(),
                ENTITY_IN_USE.getType(),
                ENTITY_IN_USE.getTitle(),
                ENTITY_IN_USE.getDetail(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), ENTITY_IN_USE.getStatus(), request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ProblemDetail problemDetail = new ProblemDetail(
                ENTITY_NOT_FOUND.getStatus().value(),
                ENTITY_NOT_FOUND.getType(),
                ENTITY_NOT_FOUND.getTitle(),
                ENTITY_NOT_FOUND.getDetail(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), ENTITY_NOT_FOUND.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = new ProblemDetail(
                status.value(),
                HTTP_MESSAGE_NOT_READABLE.getType(),
                HTTP_MESSAGE_NOT_READABLE.getTitle(),
                HTTP_MESSAGE_NOT_READABLE.getDetail(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
    }

    //    TODO(ver aulas )

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

}
