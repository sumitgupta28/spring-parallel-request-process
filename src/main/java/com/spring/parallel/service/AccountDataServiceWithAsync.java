package com.spring.parallel.service;

import com.spring.parallel.mock.dto.AccountBasicDetails;
import com.spring.parallel.mock.dto.AccountData;
import com.spring.parallel.mock.dto.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountDataServiceWithAsync {

    private final AccountBasicDetailsAsyncAccess accountBasicDetailsAsyncAccess;
    private final AccountAddressAsyncAccess accountAddressAsyncAccess;

    public AccountData getAccountDataByAccountId(final String accountId) {
        CompletableFuture<AccountBasicDetails> accountBasicDetails = accountBasicDetailsAsyncAccess.getAccountBasicDetails(accountId);
        CompletableFuture<List<Address>> addresses = accountAddressAsyncAccess.getAddress(accountId);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(accountBasicDetails, addresses);

        CompletableFuture<AccountData> finalResultAccountDataCompletableFuture = combinedFuture.thenApply(var -> {
            return AccountData.builder().accountBasicDetails(accountBasicDetails.join()).addresses(addresses.join()).build();
        });
        return finalResultAccountDataCompletableFuture.join();
    }


}
