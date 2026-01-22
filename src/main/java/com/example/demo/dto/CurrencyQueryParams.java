package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyQueryParams {

    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid currency code")
    private String fromCurrency = "INR";

    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid currency code")
    private String toCurrency = "USD";

    @Min(value = 1, message = "Units must be at least 1")
    @Max(value = 1000000, message = "Units cannot exceed 1,000,000")
    private Long units = 500L;
}
