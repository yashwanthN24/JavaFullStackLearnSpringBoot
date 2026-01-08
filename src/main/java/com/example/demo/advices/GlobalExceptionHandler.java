package com.example.demo.advices;


import com.example.demo.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException e){
        ApiError error = ApiError.builder().status(HttpStatus.NOT_FOUND).message(Optional.of(e.getMessage()).orElse("Resource not found")).build();
        return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class) // SInce we are ctahing the parent Exception class all exceptions get caught here
    public ResponseEntity<ApiError> handleInternalServerError(Exception e){
        ApiError error = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(
                        Optional.ofNullable(e.getMessage())
                                .orElse("An unexpected error occurred")
                )
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException e){
        List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError error = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(
                        "Input Validation Failed"
                )
                .subErrors(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}

// u can use try catch at controller level specific to each route handler if you need extra safetly like for payment service and all

//But global ExceptionHanlder works well for most cases as it hanldes exceptions across the application