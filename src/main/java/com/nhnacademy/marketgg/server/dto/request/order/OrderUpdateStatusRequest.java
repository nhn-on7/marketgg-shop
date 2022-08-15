package com.nhnacademy.marketgg.server.dto.request.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class OrderUpdateStatusRequest {

    @NotNull
    private String status;

}
