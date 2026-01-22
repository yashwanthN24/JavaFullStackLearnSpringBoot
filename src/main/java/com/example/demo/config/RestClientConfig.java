package com.example.demo.config;



import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Configuration
public class RestClientConfig {

    @Value("${employeeService.base.url}")
    private String BASE_URL;

    @Bean
    @Qualifier("employeeRestClient")
    RestClient employeeServiceRestClient() {
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new RuntimeException("Server error occurred");
                }) // this is global level error handling of API Client
                .build();
    }

    @Bean
    @Qualifier("jsonplaceholderapi")
    RestClient getJsonPlaceHolderPostsClient(){
        return RestClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .defaultHeader(CONTENT_TYPE , APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    @Qualifier("currency")
    RestClient getCurrenciesClient(){
        return RestClient.builder()
                .baseUrl("https://api.freecurrencyapi.com/v1/latest")
                .defaultHeader(CONTENT_TYPE , APPLICATION_JSON_VALUE)
                .build();

    }

}

