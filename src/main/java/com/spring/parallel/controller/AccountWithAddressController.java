package com.spring.parallel.controller;

import com.spring.parallel.mock.dto.AccountData;
import com.spring.parallel.service.AccountDataServiceWithAsync;
import com.spring.parallel.service.AccountDataServiceWithCompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountWithAddressController {

    private final AccountDataServiceWithCompletableFuture accountDataServiceWithCompletableFuture;
    private final AccountDataServiceWithAsync accountDataServiceWithAsync;

    // Endpoint to trigger single data fetching
    @GetMapping("/completableFuture/{accountId}")
    public ResponseEntity<AccountData> getAccountDetailsUsingCompletableFuture(@PathVariable final String accountId) {
        return ResponseEntity.ok(accountDataServiceWithCompletableFuture.getAccountDataByAccountId(accountId));
    }


    // Endpoint to trigger single data fetching
    @GetMapping("/async/{accountId}")
    public ResponseEntity<AccountData> getAccountDetailsUsingAsync(@PathVariable final String accountId) {
        return ResponseEntity.ok(accountDataServiceWithAsync.getAccountDataByAccountId(accountId));
    }
}
