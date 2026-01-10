package com.example.demo.DTOs;


import com.example.demo.annotations.DepartmentPasswordValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private Long id;  // Null on create/update input

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Active status required")
    private Boolean active;

    private Instant createdAt;  // Null on input, set on response

//    @DepartmentPasswordValidation
//    private String password;
}


