package com.spring.parallel.service;

import com.spring.parallel.mock.dto.AccountBasicDetails;
import com.spring.parallel.mock.dto.AccountData;
import com.spring.parallel.mock.dto.Address;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDataService {

    private WebClient webclient;

    public static AccountData buildAccountData(AccountBasicDetails accountBasicDetails, List<Address> address) {
        return AccountData.builder().accountBasicDetails(accountBasicDetails).addresses(address).build();
    }

    @PostConstruct
    void postConstruct() {
        String internalBaseUrl = "http://localhost:8080";
        webclient = WebClient.builder().baseUrl(internalBaseUrl).build();
    }

    public Mono<AccountData> getAccountData(String accountId) {
        Mono<AccountBasicDetails> accountBasicDetails = getAccountBasicDetails(accountId);
        Flux<Address> addressFlux = getAddress(accountId);
        return Mono.zip(accountBasicDetails, addressFlux.collectList(), AccountDataService::buildAccountData);
    }

    private Flux<Address> getAddress(String accountId) {
        return this.webclient
                .get()
                .uri("/mock/vi/address/" + accountId)
                .retrieve()
                .bodyToFlux(Address.class);
    }

    private Mono<AccountBasicDetails> getAccountBasicDetails(String accountId) {

        return this.webclient
                .get()
                .uri("/mock/vi/account/" + accountId)
                .retrieve()
                .bodyToMono(AccountBasicDetails.class);
    }
}
