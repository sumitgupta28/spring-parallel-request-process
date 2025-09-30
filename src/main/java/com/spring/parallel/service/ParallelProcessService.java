package com.spring.parallel.service;

import com.spring.parallel.mock.dto.MockResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParallelProcessService {

    private WebClient webclient;


    @PostConstruct
    void postConstruct() {
        String internalBaseUrl = "http://localhost:8080";
        webclient = WebClient.builder().baseUrl(internalBaseUrl).build();
    }

    public Flux<List<MockResponse>> fetchData() {
        return Flux.zip(
                (getDataForId(1)),
                (getDataForId(2)),
                this::gatherObjects);
    }

    private List<MockResponse> gatherObjects(MockResponse mockResponse1, MockResponse mockResponse2) {
        log.info("gatherObjects for {} , {} ", mockResponse1.getMessage(), mockResponse2.getMessage());
        List<MockResponse> mockResponseList = new ArrayList<>();
        mockResponseList.add(mockResponse1);
        mockResponseList.add(mockResponse2);
        return mockResponseList;
    }

    private Flux<MockResponse> getDataForId(int id) {
        return this.webclient
                .get()
                .uri("/mock/data/" + id)
                .retrieve()
                .bodyToFlux(MockResponse.class);
    }


}
