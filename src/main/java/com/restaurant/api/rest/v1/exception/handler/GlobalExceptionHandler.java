package com.restaurant.api.rest.v1.exception.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.restaurant.api.rest.v1.exception.BadRequestException;
import com.restaurant.api.rest.v1.exception.EntityAlreadyExistsException;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.restaurant.api.rest.v1.exception.handler.ProblemType.*;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ProblemDetail problemDetail = new ProblemDetail(
                BAD_REQUEST.getStatus().value(),
                BAD_REQUEST.getType(),
                BAD_REQUEST.getTitle(),
                BAD_REQUEST.getDetail(),
                ex.getMessage(),
                LocalDateTime.now(),
                null
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
                LocalDateTime.now(),
                null
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
                LocalDateTime.now(),
                null
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), ENTITY_IN_USE.getStatus(), request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ProblemDetail problemDetail = new ProblemDetail(
                NO_RESOURCE_FOUND.getStatus().value(),
                NO_RESOURCE_FOUND.getType(),
                NO_RESOURCE_FOUND.getTitle(),
                NO_RESOURCE_FOUND.getDetail(),
                ex.getMessage(),
                LocalDateTime.now(),
                null
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), NO_RESOURCE_FOUND.getStatus(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaughtException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        ProblemDetail problemDetail = new ProblemDetail(
                INTERNAL_SERVER_ERROR.getStatus().value(),
                INTERNAL_SERVER_ERROR.getType(),
                INTERNAL_SERVER_ERROR.getTitle(),
                INTERNAL_SERVER_ERROR.getDetail(),
                INTERNAL_SERVER_ERROR.getDetail(),
                LocalDateTime.now(),
                null
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), INTERNAL_SERVER_ERROR.getStatus(), request);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<?> handleAuthorizationDeniedException(AuthorizationDeniedException ex, WebRequest request) {
        log.error(ex.getMessage());
        ProblemDetail problemDetail = new ProblemDetail(
                AUTHORIZATION_DENIED.getStatus().value(),
                AUTHORIZATION_DENIED.getType(),
                AUTHORIZATION_DENIED.getTitle(),
                AUTHORIZATION_DENIED.getDetail(),
                AUTHORIZATION_DENIED.getDetail(),
                LocalDateTime.now(),
                null
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), AUTHORIZATION_DENIED.getStatus(), request);
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<?> handleCannotCreateTransactionException(CannotCreateTransactionException ex, WebRequest request) {
        log.error(ex.getMessage());
        ProblemDetail problemDetail = new ProblemDetail(
                CANNOT_CREATE_TRANSACTION.getStatus().value(),
                CANNOT_CREATE_TRANSACTION.getType(),
                CANNOT_CREATE_TRANSACTION.getTitle(),
                CANNOT_CREATE_TRANSACTION.getDetail(),
                CANNOT_CREATE_TRANSACTION.getDetail(),
                LocalDateTime.now(),
                null
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), CANNOT_CREATE_TRANSACTION.getStatus(), request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        ProblemDetail problemDetail = new ProblemDetail(
                BAD_REQUEST.getStatus().value(),
                BAD_REQUEST.getType(),
                BAD_REQUEST.getTitle(),
                ex.getMessage(),
                BAD_REQUEST.getDetail(),
                LocalDateTime.now(),
                null
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), BAD_REQUEST.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException)
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        if (rootCause instanceof PropertyBindingException)
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);

        ProblemDetail problemDetail = new ProblemDetail(
                status.value(),
                HTTP_MESSAGE_NOT_READABLE.getType(),
                HTTP_MESSAGE_NOT_READABLE.getTitle(),
                HTTP_MESSAGE_NOT_READABLE.getDetail(),
                ex.getMessage(),
                LocalDateTime.now(),
                null
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    private ResponseEntity<?> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex, WebRequest request) {
        ProblemDetail problemDetail = new ProblemDetail(
                BAD_REQUEST.getStatus().value(),
                BAD_REQUEST.getType(),
                BAD_REQUEST.getTitle(),
                ex.getMessage(),
                BAD_REQUEST.getDetail(),
                LocalDateTime.now(),
                null
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), BAD_REQUEST.getStatus(), request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
        String detail = String.format("The property '%s' received an invalid value '%s'. " +
                        "Please, check your request and send the value compatible with type '%s'",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        ProblemDetail problemDetail = new ProblemDetail(
                status.value(),
                INVALID_FORMAT.getType(),
                INVALID_FORMAT.getTitle(),
                detail,
                detail,
                LocalDateTime.now(),
                null
        );
        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException)
            return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers, status, request);
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String detail = String.format("The URL parameter '%s' has received the value '%s', that is an invalid type. " +
                "Fix it and inform a value compatible with type %s", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        ProblemDetail problemDetail = new ProblemDetail(
                status.value(),
                METHOD_ARGUMENT_TYPE_MISMATCH.getType(),
                METHOD_ARGUMENT_TYPE_MISMATCH.getTitle(),
                detail,
                detail,
                LocalDateTime.now(),
                null
        );
        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ProblemDetail.Field> fields = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    String message = messageSource.getMessage(fieldError, Locale.US);
                    return new ProblemDetail.Field(fieldError.getField(), message);
                }).toList();
        ProblemDetail problemDetail = new ProblemDetail(
                METHOD_ARGUMENT_NOT_VALID.getStatus().value(),
                METHOD_ARGUMENT_NOT_VALID.getType(),
                METHOD_ARGUMENT_NOT_VALID.getTitle(),
                ex.getMessage(),
                METHOD_ARGUMENT_NOT_VALID.getDetail(),
                LocalDateTime.now(),
                fields
        );
        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String detail = String.format("The resource '%s' you are trying to access does not exists", ex.getResourcePath());
        ProblemDetail problemDetail = new ProblemDetail(
                NO_RESOURCE_FOUND.getStatus().value(),
                NO_RESOURCE_FOUND.getType(),
                NO_RESOURCE_FOUND.getTitle(),
                detail,
                detail,
                LocalDateTime.now(),
                null
        );
        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
        String detail = String.format("The property '%s' you are trying to reach does not exists. " +
                "Please, fix or remove it and try again", path);
        ProblemDetail problemDetail = new ProblemDetail(
                status.value(),
                PROPERTY_BIND_EXCEPTION.getType(),
                PROPERTY_BIND_EXCEPTION.getTitle(),
                detail,
                detail,
                LocalDateTime.now(),
                null
        );
        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

}
