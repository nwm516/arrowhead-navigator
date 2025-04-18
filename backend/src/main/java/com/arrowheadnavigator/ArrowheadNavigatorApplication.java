package com.arrowheadnavigator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Main entry point for the Arrowhead Navigator application.
 * This application helps businesses identify weather-related risks for delivery routes.
 */
@SpringBootApplication
@EnableFeignClients
public class ArrowheadNavigatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArrowheadNavigatorApplication.class, args);
    }

    /**
     * RestTemplate bean for making HTTP requests to external APIs.
     * @return Configured RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}