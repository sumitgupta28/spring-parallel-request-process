package com.spring.parallel.config;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class FakerConfig {

    @Bean
    public Faker faker() {
        // You can specify a locale for localized fake data, e.g., Locale.US, new Locale("tr")
        return new Faker(new Locale("en-US"));
    }
}
