package com.example.demo.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDate dateOfJoining;

    @JsonProperty("isActive")
    private Boolean isActive; // jackson picks up fields based on getter so it sees as Active not isActive either chnage it at getter level
//    or change it here in property


}
