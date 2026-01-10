package com.example.demo.annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD , ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DepartmentPasswordValidator.class})
public @interface DepartmentPasswordValidation {
    String message() default "Password must include one Uppercase , one Lowercase , one special character and minimum length of 10 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
