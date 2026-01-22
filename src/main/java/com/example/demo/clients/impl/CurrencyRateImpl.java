package com.example.demo.clients.impl;

import com.example.demo.clients.CurrencyRate;
import com.example.demo.dto.CurrencyApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CurrencyRateImpl implements CurrencyRate {

    private final Logger log = LoggerFactory.getLogger(CurrencyRateImpl.class);

    private final RestClient getCurrenciesClient;

    @Value("${currency.api}")
    private  String apiKey;

    @Override
    public BigDecimal toCurrency(String fromCurrency, String toCurrency, Long units) {
        CurrencyApiResponse response =  getCurrenciesClient.get()
                .uri("?apikey={key}&base_currency={base}&currencies={curr}",
                        apiKey, fromCurrency, toCurrency).retrieve().body(CurrencyApiResponse.class);
        Double rate = response.getData().get(toCurrency);
        BigDecimal rateDecimal = BigDecimal.valueOf(rate);
        BigDecimal result = rateDecimal.multiply(BigDecimal.valueOf(units))
                .setScale(2, RoundingMode.HALF_UP);

        log.info("Converted {} {} to {} {}: {}",
                units, fromCurrency, toCurrency, result, toCurrency);

        return result;
    }
}
