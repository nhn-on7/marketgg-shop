package com.nhnacademy.marketgg.server.dto.response.deliveryaddress;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeliveryAddressResponse {

    private final Long id;
    private final Boolean isDefaultAddress;
    private final Integer zipCode;
    private final String address;
    private final String detailAddress;

}
