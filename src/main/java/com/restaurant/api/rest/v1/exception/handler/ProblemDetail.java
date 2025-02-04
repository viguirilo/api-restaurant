package com.restaurant.api.rest.v1.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<Field> fields;

    @AllArgsConstructor
    @Data
    public static class Field {
        private String name;
        private String userMessage;
    }

}
