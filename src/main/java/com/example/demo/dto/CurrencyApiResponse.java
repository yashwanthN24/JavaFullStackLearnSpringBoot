package com.example.demo.dto;


import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CurrencyApiResponse {
    private Map<String, Double> data;
}
