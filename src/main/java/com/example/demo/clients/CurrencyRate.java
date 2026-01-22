package com.example.demo.clients;

import java.math.BigDecimal;

public interface CurrencyRate {

    BigDecimal toCurrency(String fromCurrency , String toCurrency , Long units);
}
