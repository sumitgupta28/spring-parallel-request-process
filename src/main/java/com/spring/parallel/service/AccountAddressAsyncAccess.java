package com.spring.parallel.service;

import com.spring.parallel.mock.dto.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountAddressAsyncAccess {

    private final RestTemplate restTemplate;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<Address>> getAddress(String accountId) {
        log.info(" {} AccountAddressAsyncAccess Get Address for Account Id {} ", Thread.currentThread().getName(), accountId);
        String url = "http://localhost:8080/mock/vi/address/" + accountId; // Replace with your actual API endpoint
        ResponseEntity<List<Address>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null, // No request body for GET
                new ParameterizedTypeReference<List<Address>>() {
                } // Provides type information
        );
        return CompletableFuture.completedFuture(response.getBody());
    }
}
