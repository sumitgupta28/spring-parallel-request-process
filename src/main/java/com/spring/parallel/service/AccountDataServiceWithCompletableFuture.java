package com.spring.parallel.service;

import com.spring.parallel.mock.dto.AccountBasicDetails;
import com.spring.parallel.mock.dto.AccountData;
import com.spring.parallel.mock.dto.Address;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountDataServiceWithCompletableFuture {

    private final RestTemplate restTemplate;
    private final Executor threadPoolTaskExecutor;

    @PostConstruct
    void init() {
        for (int i = 1; i <= 5; i++) {
            threadPoolTaskExecutor.execute(() -> {
                log.info("Job  is running on {}", Thread.currentThread().getName());
            });
        }
    }


    public AccountData getAccountDataByAccountId(final String accountId) {
        CompletableFuture<AccountBasicDetails> accountBasicDetailsCompletableFuture = CompletableFuture.supplyAsync(() -> getAccountBasicDetails(accountId), threadPoolTaskExecutor);
        CompletableFuture<List<Address>> addressCompletableFuture = CompletableFuture.supplyAsync(() -> getAddress(accountId), threadPoolTaskExecutor);
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(accountBasicDetailsCompletableFuture, accountBasicDetailsCompletableFuture);

        CompletableFuture<AccountData> finalResultAccountDataCompletableFuture = combinedFuture.thenApply(var -> {
            return AccountData.builder().accountBasicDetails(accountBasicDetailsCompletableFuture.join()).addresses(addressCompletableFuture.join()).build();
        });
        return finalResultAccountDataCompletableFuture.join();
    }


    private List<Address> getAddress(String accountId) {
        log.info(" {} AccountDataServiceWithCompletableFuture Get Address for Account Id {} ", Thread.currentThread().getName(), accountId);

        String url = "http://localhost:8080/mock/vi/address/" + accountId; // Replace with your actual API endpoint
        ResponseEntity<List<Address>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null, // No request body for GET
                new ParameterizedTypeReference<List<Address>>() {
                } // Provides type information
        );
        return response.getBody();
    }

    private AccountBasicDetails getAccountBasicDetails(String accountId) {
        log.info(" {} AccountDataServiceWithCompletableFuture Get AccountBasicDetails for Account Id {} ", Thread.currentThread().getName(), accountId);
        return this.restTemplate.getForObject("http://localhost:8080/mock/vi/account/" + accountId, AccountBasicDetails.class);
    }
}
