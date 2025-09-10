package com.spring.parallel.controller;

import com.spring.parallel.mock.dto.MockResponse;
import com.spring.parallel.service.ParallelProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ParallelController {

    private final ParallelProcessService parallelProcessService;

    // Endpoint to trigger single data fetching
    @GetMapping("/fetchData")
    public CompletableFuture<MockResponse> getData() {
        log.info("Request received for 'fetchData' endpoint.");
        final String url = "http://localhost:8080/mock/data/1";
        return parallelProcessService.fetchData(url);
    }

    // Endpoint to trigger parallel data fetching
    @GetMapping("/parallel")
    public CompletableFuture<List<MockResponse>> getParallelData() {
        log.info("Request received for 'parallel' endpoint.");
        CompletableFuture<List<MockResponse>> listCompletableFuture = parallelProcessService.fetchAllData();
        log.info("Request processing completed for 'parallel' endpoint.");
        return listCompletableFuture;
    }

    // Endpoint to trigger parallel data fetching with exception in one task
    @GetMapping("/parallelWithExceptionIOneTask")
    public CompletableFuture<List<MockResponse>> getParallelWithExceptionIOneTask() {
        log.info("Request received for 'parallelWithExceptionIOneTask' endpoint.");
        CompletableFuture<List<MockResponse>> listCompletableFuture = parallelProcessService.fetchAllDataWithErrorInOneCall();
        log.info("Request processing completed for 'parallelWithExceptionIOneTask' endpoint.");
        return listCompletableFuture;
    }

    // Endpoint to trigger parallel data fetching with a global timeout
    @GetMapping("/parallelWithGlobalTimeout")
    public CompletableFuture<List<MockResponse>> getParallelDataWithGlobalTimeout() {
        log.info("Request received for 'parallelWithTimeout' endpoint.");
        CompletableFuture<List<MockResponse>> listCompletableFuture = parallelProcessService.fetchAllDataWithTimeout(1000, 5000);
        log.info("Request processing completed for 'parallelWithTimeout' endpoint.");
        return listCompletableFuture;
    }

    // Endpoint to trigger parallel data fetching with individual task timeout
    @GetMapping("/parallelWithIndividualTaskTimeout")
    public CompletableFuture<List<MockResponse>> getParallelWithIndividualTaskTimeout() {
        log.info("Request received for 'parallelWithIndividualTaskTimeout' endpoint.");
        CompletableFuture<List<MockResponse>> listCompletableFuture = parallelProcessService.fetchAllDataWithTimeout(10000, 2000);
        log.info("Request processing completed for 'parallelWithIndividualTaskTimeout' endpoint.");
        return listCompletableFuture;
    }


}
