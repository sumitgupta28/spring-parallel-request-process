package com.spring.parallel.mock.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountBasicDetails {

    private String accountId;
    private String firstName;
    private String lastName;
    private String accountType;
    private String dateOfBirth;

}
