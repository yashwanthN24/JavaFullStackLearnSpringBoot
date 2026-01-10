package com.example.demo.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DepartmentPasswordValidator implements ConstraintValidator<DepartmentPasswordValidation , String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null || password.length() < 10) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if ("!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(c) != -1) {
                hasSpecial = true;
            }
        }

        return hasUpper && hasLower && hasSpecial;


    }
}
