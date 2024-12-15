package com.restaurant.api.rest.v1.exceptionHandler;

import com.restaurant.api.rest.v1.exception.MyBadRequestException;
import com.restaurant.api.rest.v1.exception.MyEntityAlreadyExistsException;
import com.restaurant.api.rest.v1.exception.MyEntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(MyEntityAlreadyExistsException.class)
    public ResponseEntity<?> handleMyEntityAlreadyExistsException(MyEntityAlreadyExistsException ex, WebRequest webRequest) {
        ProblemDetail problemDetail = new ProblemDetail(
                HttpStatus.CONFLICT.value(),
                ENTITY_ALREADY_EXISTS.getType(),
                ENTITY_ALREADY_EXISTS.getTitle(),
                ex.getMessage(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }

    @ExceptionHandler(MyBadRequestException.class)
    public ResponseEntity<?> handleMyBadRequestException(MyBadRequestException ex, WebRequest webRequest) {
        ProblemDetail problemDetail = new ProblemDetail(
                HttpStatus.BAD_REQUEST.value(),
                BAD_REQUEST.getType(),
                BAD_REQUEST.getTitle(),
                ex.getMessage(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(MyEntityNotFoundException.class)
    public ResponseEntity<?> handleMyEntityNotFoundException(MyEntityNotFoundException ex, WebRequest webRequest) {
        ProblemDetail problemDetail = new ProblemDetail(
                HttpStatus.NOT_FOUND.value(),
                ENTITY_NOT_FOUND.getType(),
                ENTITY_NOT_FOUND.getTitle(),
                ex.getMessage(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

//        receber o problema na exception e continuar sua montagem para poder passar para o método handleExceptionInternal
//        ou seja, uma CityNotFoundException lança um ProblemDetail com type, title, detail e instance
//        criar um enum na pasta exceptionHandler chamado ProblemType para abrigar os diversos tipos de problema
//        montar o problema aqui

//    TODO(criar todas as classes de erros)
//    TODO(colocar mais handlers aqui)
//    TODO(ver aulas 10, 12, 13, 18)
//    TODO(retorna apenas uma mensagem que é usada como detalhe e mensagem ao usuário)

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

}
