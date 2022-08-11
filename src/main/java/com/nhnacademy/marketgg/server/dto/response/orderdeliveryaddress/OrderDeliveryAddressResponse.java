package com.nhnacademy.marketgg.server.dto.response.orderdeliveryaddress;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderDeliveryAddressResponse {

    private final Integer zipCode;

    private final String address;

    private final String detailAddress;

}
