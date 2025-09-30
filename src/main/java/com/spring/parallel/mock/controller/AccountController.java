package com.spring.parallel.mock.controller;

import com.spring.parallel.mock.dto.AccountBasicDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/mock/vi/account")
@RequiredArgsConstructor
@Tag(name = "Mock Downstream")
public class AccountController {

    private final Faker faker;

    @Operation(summary = "/account Mock API ", description = "Prepare Mock Account Details and Return")
    @GetMapping("/{accountId}")
    ResponseEntity<AccountBasicDetails> getAccountById(@PathVariable String accountId) {
        return getAccount(accountId);
    }


    private ResponseEntity<AccountBasicDetails> getAccount(String accountId) {

        List<String> accountTypes = List.of("Savings", "Checking");
        LocalDate dob = faker.timeAndDate().birthday(18, 65);
        // Create a DateTimeFormatter with the desired pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        // Format the LocalDate to a String
        String formattedDate = dob.format(formatter);

        return ResponseEntity.ok(AccountBasicDetails.builder()
                .accountId(accountId)
                .accountType(faker.options().nextElement(accountTypes))
                .dateOfBirth(formattedDate)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build());
    }

}
