package com.nhnacademy.marketgg.server.dto.request.deliveryaddress;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
public class DeliveryAddressCreateRequest {

    @NotNull
    private boolean isDefaultAddress;

    @NotNull
    @Positive
    private Integer zipCode;

    @NotBlank
    @Size(min = 5, max = 100)
    private String address;

    @NotBlank
    @Size(min = 1, max = 100)
    private String detailAddress;

}
