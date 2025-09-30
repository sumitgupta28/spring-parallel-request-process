package com.spring.parallel.mock.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountData {

    private AccountBasicDetails accountBasicDetails;
    private List<Address> addresses;
}
