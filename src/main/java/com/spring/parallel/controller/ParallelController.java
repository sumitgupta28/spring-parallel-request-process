package com.spring.parallel.controller;

import com.spring.parallel.mock.dto.AccountData;
import com.spring.parallel.mock.dto.MockResponse;
import com.spring.parallel.service.AccountDataService;
import com.spring.parallel.service.ParallelProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Parallel APIs")
public class ParallelController {

    private final ParallelProcessService parallelProcessService;
    private final AccountDataService accountDataService;

    // Endpoint to trigger single data fetching
    @GetMapping("/fetchData")
    @Operation(summary = "Get Data ", description = "Simple API to Demonstrate the API Parallel API calls")
    public Flux<List<MockResponse>> getData() {
        return parallelProcessService.fetchData();
    }

    // Endpoint to trigger single data fetching
    @GetMapping("/getAccountData/{accountId}")
    @Operation(summary = "Get Account Details from /account and /address ", description = "This API calls 2 downstream APIs /account and /address in Parallel and combine the result before it returns")
    public Mono<AccountData> getAccountData(@PathVariable final String accountId) {
        return accountDataService.getAccountData(accountId);
    }

}
