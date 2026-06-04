package com.blumbit.compras_ventas.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blumbit.compras_ventas.validation.spec.IUniqueNameChecker;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueNameValidator implements ConstraintValidator<UniqueName, String>{

    @Autowired
    private IUniqueNameChecker uniqueNameChecker;

    private String fieldName;

    @Override
    public void initialize(UniqueName constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isBlank()) {
            return true;
        }

        boolean isUnique = uniqueNameChecker.isUniqueName(value);
        if(!isUnique) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El valor del '" + fieldName + "' ya existe")
            .addConstraintViolation();
            return false;
        }

        return true;
    }

    
}
