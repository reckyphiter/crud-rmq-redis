package com.reckyphiter.crudcommon.util;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Recky Phiter
 */
public class EnumValidator implements ConstraintValidator<Enum, String>
{
    private Enum annotation;

    @Override
    public void initialize(Enum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {
        final Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (StringUtils.equals(valueForValidation, enumValue.toString()) ||
                        (this.annotation.ignoreCase() && StringUtils.equalsIgnoreCase(valueForValidation, enumValue.toString()))) {
                    return true;
                }
            }
        }

        return false;
    }
}