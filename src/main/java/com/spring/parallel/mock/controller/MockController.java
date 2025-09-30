package com.spring.parallel.mock.controller;

import com.spring.parallel.mock.dto.MockResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/mock")
@Tag(name = "Down Stream Mock APIs")
public class MockController {


    @GetMapping("/data/{id}")
    public MockResponse extractMockData(@PathVariable String id) throws InterruptedException {
        log.info("Received a request for mock data with id: {}", id);
        Thread.sleep(3000); // Adding a delay to simulate real-world scenarios
        return MockResponse.builder().id(id).message("Mock data for ID " + id).build();
    }

    @GetMapping("/data/{id}/exception")
    public MockResponse getMockDataException(@PathVariable String id) {
        log.info("Received a request for mock data with id: {}", id);
        throw new RuntimeException("Something went wrong");
    }

}
