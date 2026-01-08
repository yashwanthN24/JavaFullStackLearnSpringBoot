package com.example.demo.DTOs;

import com.example.demo.annotations.EmployeeRoleValidation;
import com.example.demo.annotations.PrimeNumberCheckValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;

// Markers (empty)
 interface BasicGroup {}
 interface CustomGroup {}

//For Grouping the order of execution of annoation for validations
//By default all annotation have a undefined order of execution
// To fix this order we use GroupSequence


@GroupSequence({ BasicGroup.class, CustomGroup.class, EmployeeDTO.class })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "Name Field is required to create an employee")
    @Size(min = 3 , max = 14 , message = "Name length must be in the range of [3 , 14]")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Should be a valid email format")
    private String email;

    @NotNull(message = "Age of the Employee must not be blank")
    @Max(value = 80 , message = "Age of employee must not be greater than 80")
    @Min(value = 18 , message = "Age of employee must be 18 or greater")
    private Integer age;




//    @NotNull(message = "Role must be admin or user")
@NotBlank(message = "Role of employee cannot be blank" , groups = BasicGroup.class)
    @EmployeeRoleValidation(groups = CustomGroup.class)

//Because by default there iis not fixed order in which the Annotation execute so we are defining this GroupSuence
//Although remember when using custom annotation use only that annotation alone and let it handle all cases (This is common in codebases)
//
//    @Pattern(regexp = "^(ADMIN|USER)$" , message = "Role of employee can either be USER or ADMIN")

    private  String role; // ADMIN | USER


    @NotNull(message = "salary of the employee should be not null")
    @Positive(message = "salary should be positive value ")
    @Digits(integer = 6 , fraction = 2 , message = "The salary can be in the form XXXXX.YY")
    @DecimalMax(value = "100000.99")
    @DecimalMin(value = "100.50")
    private BigDecimal salary;

    @PastOrPresent(message = "DateOfJoining Field in employee cannot be in the future")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "Employee should be active")
    @JsonProperty("isActive")
    private Boolean isActive; // jackson picks up fields based on getter so it sees as Active not isActive either chnage it at getter level
//    or change it here in property

//    dont use is Prefix in DTO's
    @PrimeNumberCheckValidation
    private Integer port;


}
