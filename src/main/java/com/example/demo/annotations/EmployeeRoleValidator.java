package com.example.demo.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class EmployeeRoleValidator implements ConstraintValidator<EmployeeRoleValidation , String> {


    @Override
    public boolean isValid(String inputRole, ConstraintValidatorContext constraintValidatorContext) {
        if(inputRole == null) return false  ;
        // but true means bussiness logically not correct but if i say true only it will get handled
        List<String> roles = List.of("USER" , "ADMIN");
        return roles.contains(inputRole);
    }

}
