package com.example.demo.controllers;

import com.example.demo.clients.CurrencyRate;
import com.example.demo.clients.impl.CurrencyRateImpl;
import com.example.demo.dto.CurrencyQueryParams;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/convertCurrency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyRate currencyService;

    @GetMapping
    public BigDecimal convertCurrency(@Valid CurrencyQueryParams queryParams){

        return currencyService.toCurrency(queryParams.getFromCurrency(), queryParams.getToCurrency(), queryParams.getUnits());



    }
}
