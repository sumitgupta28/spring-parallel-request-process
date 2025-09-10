package com.spring.parallel.service;

import com.spring.parallel.mock.dto.MockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParallelProcessService {

    private final RestTemplate restTemplate;
    private final Executor executor = Executors.newFixedThreadPool(10);


    public CompletableFuture<MockResponse> fetchData(String url) {
        return CompletableFuture.supplyAsync(() -> restTemplate.getForObject(url, MockResponse.class), executor);
    }

    private CompletableFuture<MockResponse> fetchDataWithTimeout(String url, long timeout) {
        CompletableFuture<MockResponse> completableFuture = CompletableFuture.supplyAsync(() -> restTemplate.getForObject(url, MockResponse.class), executor);
        return completableFuture.orTimeout(timeout, TimeUnit.MILLISECONDS)
                .exceptionally(throwable -> {
                    if (throwable instanceof TimeoutException) {
                        log.error("TimeoutException while fetching data from {} ", url, throwable);
                    } else {
                        log.error("Other Exception while fetching data from {} ", url, throwable);
                    }
                    return null;
                });
    }

    private CompletableFuture<MockResponse> fetchDataWithHandling(String url) {
        return CompletableFuture.supplyAsync(() -> restTemplate.getForObject(url, MockResponse.class), executor).handle(
                (result, exception) -> {
                    if (exception != null) {
                        log.error("Other Exception while fetching data from {} ", url, exception);
                        return null;
                    }
                    return result;
                }
        );
    }

    // Fetches data from multiple URLs in parallel and aggregates the results
    public CompletableFuture<List<MockResponse>> fetchAllData() {
        CompletableFuture<MockResponse> request1 = fetchData("http://localhost:8080/mock/data/1");
        CompletableFuture<MockResponse> request2 = fetchData("http://localhost:8080/mock/data/2");
        CompletableFuture<MockResponse> request3 = fetchData("http://localhost:8080/mock/data/3");

        return CompletableFuture.allOf(request1, request2, request3)
                .thenApply(v -> Stream.of(request1, request2, request3)
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .toList()
                );
    }

    // Fetches data from multiple URLs in parallel and aggregates the results
    public CompletableFuture<List<MockResponse>> fetchAllDataWithTimeout(long globalTimeout, long individualTaskTimeout) {
        CompletableFuture<MockResponse> request1 = fetchDataWithTimeout("http://localhost:8080/mock/data/1", individualTaskTimeout);
        CompletableFuture<MockResponse> request2 = fetchDataWithTimeout("http://localhost:8080/mock/data/2", individualTaskTimeout);
        CompletableFuture<MockResponse> request3 = fetchDataWithTimeout("http://localhost:8080/mock/data/3", individualTaskTimeout);
        return CompletableFuture.allOf(request1, request2, request3)
                .orTimeout(globalTimeout, TimeUnit.MILLISECONDS)
                .handle((result, ex) -> {
                    if (ex instanceof TimeoutException) {
                        log.error("GlobalTimeout TimeoutException while fetching data", ex);
                    } else {
                        log.error("Other Exception while fetching data ", ex);
                    }
                    return Collections.emptyList();
                })
                .thenApply(v -> Stream.of(request1, request2, request3)
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .toList()
                );
    }


    public CompletableFuture<List<MockResponse>> fetchAllDataWithErrorInOneCall() {
        CompletableFuture<MockResponse> request1 = fetchDataWithHandling("http://localhost:8080/mock/data/1");
        CompletableFuture<MockResponse> request2 = fetchDataWithHandling("http://localhost:8080/mock/data/2/exception");
        CompletableFuture<MockResponse> request3 = fetchDataWithHandling("http://localhost:8080/mock/data/3");
        return CompletableFuture.allOf(request1, request2, request3)
                .thenApply(v -> Stream.of(request1, request2, request3)
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .toList()
                );
    }


}
