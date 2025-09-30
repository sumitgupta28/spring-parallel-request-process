package com.spring.parallel.mock.controller;

import com.spring.parallel.mock.dto.Address;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/mock/vi/address")
@RequiredArgsConstructor
@Tag(name = "Mock Downstream")
public class AddressController {


    private final Faker faker;

    @GetMapping("/{accountId}")
    @Operation(summary = "/address Mock API ", description = "Prepare Mock Address Details and Return")
    Flux<Address> getAddressForId(@PathVariable String accountId) {
        return Flux.concat(getAddress(accountId), getAddress(accountId));
    }


    private Flux<Address> getAddress(String accountId) {
        return Flux.just(Address.builder()
                .accountId(accountId)
                .addressId(faker.number().randomDigit())
                .state(faker.address().state())
                .streetAddress(faker.address().streetAddress())
                .zip(faker.address().zipCodePlus4())
                .city(faker.address().city())
                .houseNo(faker.address().buildingNumber())
                .country(faker.address().country())
                .build());
    }
}
