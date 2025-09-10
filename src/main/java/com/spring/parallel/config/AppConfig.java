package com.spring.parallel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Customize the RestTemplateBuilder here if needed,
        // for example, adding interceptors, setting timeouts, etc.
        return builder.build();
    }
}
