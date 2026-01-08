package com.example.demo.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;


@Data // adds getters and setters comes from lombok dependency
@Builder // builder pattern meaning exposes a static method that return a new Object of this class upon which we can call setter to ste value method chaining

public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> subErrors;
}
