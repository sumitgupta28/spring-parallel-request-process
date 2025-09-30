package com.spring.parallel.service;

import com.spring.parallel.mock.dto.AccountBasicDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountBasicDetailsAsyncAccess {
    private final RestTemplate restTemplate;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<AccountBasicDetails> getAccountBasicDetails(String accountId) {
        log.info(" {} AccountBasicDetailsAsyncAccess Get AccountBasicDetails for Account Id {} ", Thread.currentThread().getName(), accountId);
        AccountBasicDetails accountBasicDetails = this.restTemplate.getForObject("http://localhost:8080/mock/vi/account/" + accountId, AccountBasicDetails.class);
        return CompletableFuture.completedFuture(accountBasicDetails);
    }
}
