package com.nhnacademy.marketgg.server.dto.response.deliveryaddress;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DeliveryAddressResponse {

    private final Long id;
    private final Boolean isDefaultAddress;
    private final Integer zipcode;
    private final String address;
    private final String detailAddress;

}
