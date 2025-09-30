package com.spring.parallel.mock.controller;

import com.spring.parallel.mock.dto.MockResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@RequestMapping("/mock")
@Tag(name = "Mock Downstream")
public class MockDataController {


    @GetMapping("/data/{id}")
    public Flux<MockResponse> extractMockData(@PathVariable String id) throws InterruptedException {
        log.info("Received a request for mock data with id: {}", id);

        if (id.equalsIgnoreCase("1")) {
            Thread.sleep(1000); // Adding a delay to simulate real-world scenarios
            return Flux.just(
                    MockResponse.builder().id(id).message("Mock data for ID [1] ->" + id).build()
            );
        }
        if (id.equalsIgnoreCase("2")) {
            Thread.sleep(2000); // Adding a delay to simulate real-world scenarios
            return Flux.just(
                    MockResponse.builder().id(id).message("Mock data for ID [1] ->" + id).build(),
                    MockResponse.builder().id(id).message("Mock data for ID [2] ->" + id).build()
            );
        } else {
            Thread.sleep(3000); // Adding a delay to simulate real-world scenarios
            return Flux.just(
                    MockResponse.builder().id(id).message("Mock data for ID [0] ->" + id).build()
            );
        }
    }


}
