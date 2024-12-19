package com.restaurant.api.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class ValueZeroCheckFieldValidator implements ConstraintValidator<ValueZeroCheckField, Object> {

    private String referenceField;
    private String targetField;
    private String mandatoryDescription;

    @Override
    public void initialize(ValueZeroCheckField constraintAnnotation) {
        this.referenceField = constraintAnnotation.referenceField();
        this.targetField = constraintAnnotation.targetField();
        this.mandatoryDescription = constraintAnnotation.mandatoryDescription();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            BigDecimal referenceFieldValue = (BigDecimal) BeanUtils.getPropertyDescriptor(o.getClass(), referenceField)
                    .getReadMethod().invoke(o);
            String targetFieldValue = (String) BeanUtils.getPropertyDescriptor(o.getClass(), targetField)
                    .getReadMethod().invoke(o);
            if (referenceFieldValue != null && referenceFieldValue.equals(BigDecimal.ZERO) && targetFieldValue != null) {
                return targetFieldValue.toLowerCase().contains(this.mandatoryDescription.toLowerCase());
            }
            return false;
        } catch (Exception ex) {
            throw new ValidationException(ex);
        }
    }

}
