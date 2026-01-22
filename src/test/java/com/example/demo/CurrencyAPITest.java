package com.example.demo;

import com.example.demo.clients.CurrencyRate;
import com.example.demo.clients.impl.CurrencyRateImpl;
import com.example.demo.controllers.CurrencyController;
import com.example.demo.dto.CurrencyQueryParams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class CurrencyAPITest {

    @Autowired
    private CurrencyController currencyController;

    @Test
    public void testCurrency(){
        BigDecimal result = currencyController.convertCurrency(new CurrencyQueryParams());
        System.out.println(result);
        BigDecimal result2 = currencyController.convertCurrency(new CurrencyQueryParams("USD" , "INR" , 1L));
        System.out.println(result2);
    }
}
