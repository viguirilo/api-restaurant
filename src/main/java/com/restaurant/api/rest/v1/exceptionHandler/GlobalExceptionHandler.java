package com.restaurant.api.rest.v1.exceptionHandler;

import com.restaurant.api.rest.v1.exception.BadRequestException;
import com.restaurant.api.rest.v1.exception.EntityAlreadyExistsException;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static com.restaurant.api.rest.v1.exceptionHandler.ProblemType.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex, WebRequest webRequest) {
        ProblemDetail problemDetail = new ProblemDetail(
                BAD_REQUEST.getStatus().value(),
                BAD_REQUEST.getType(),
                BAD_REQUEST.getTitle(),
                ex.getMessage(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), BAD_REQUEST.getStatus(), webRequest);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<?> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex, WebRequest webRequest) {
        ProblemDetail problemDetail = new ProblemDetail(
                ENTITY_ALREADY_EXISTS.getStatus().value(),
                ENTITY_ALREADY_EXISTS.getType(),
                ENTITY_ALREADY_EXISTS.getTitle(),
                ex.getMessage(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), ENTITY_ALREADY_EXISTS.getStatus(), webRequest);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest webRequest) {
        ProblemDetail problemDetail = new ProblemDetail(
                ENTITY_IN_USE.getStatus().value(),
                ENTITY_IN_USE.getType(),
                ENTITY_IN_USE.getTitle(),
                ex.getMessage(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), ENTITY_IN_USE.getStatus(), webRequest);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest webRequest) {
        ProblemDetail problemDetail = new ProblemDetail(
                ENTITY_NOT_FOUND.getStatus().value(),
                ENTITY_NOT_FOUND.getType(),
                ENTITY_NOT_FOUND.getTitle(),
                ex.getMessage(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), ENTITY_NOT_FOUND.getStatus(), webRequest);
    }


//    TODO(ver aulas 14, 15, 18)

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

}
