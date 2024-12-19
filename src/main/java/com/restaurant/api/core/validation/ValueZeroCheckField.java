package com.restaurant.api.core.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValueZeroCheckFieldValidator.class})
public @interface ValueZeroCheckField {

    String message() default "Mandatory terms in the restaurant name not fount";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String referenceField();

    String targetField();

    String mandatoryDescription();

}
