package com.spring.parallel.mock.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    private String accountId;
    private Integer addressId;
    private String houseNo;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String country;
}
