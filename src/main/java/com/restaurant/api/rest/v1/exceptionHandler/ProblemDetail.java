package com.restaurant.api.rest.v1.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
public class ProblemDetail {

    private Integer status; // status code original do problema
    private String type; // identifica univuocamente o erro. Pode ser um link para alguma página web
    private String title; // texto curto legível para humanos. não deve mudar para o mesmo assunto
    private String detail; // aqui é onde detalha o que exatamente aconteceu

    private String userInterfaceMessage;
    private LocalDateTime timestamp;

}
