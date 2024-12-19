package com.restaurant.api.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

    int number;

    @Override
    public void initialize(Multiple constraintAnnotation) {
        this.number = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null) {
            var valueBigDecimal = BigDecimal.valueOf(value.doubleValue());
            var numberBigDecimal = BigDecimal.valueOf(this.number);
            return valueBigDecimal.remainder(numberBigDecimal).equals(BigDecimal.ZERO);
        }
        return false;
    }

}
