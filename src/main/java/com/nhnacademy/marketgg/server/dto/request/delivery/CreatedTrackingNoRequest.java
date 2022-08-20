package com.nhnacademy.marketgg.server.dto.request.delivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
public class CreatedTrackingNoRequest {

    @NotBlank(message = "운송장 번호가 유효하지 않습니다.")
    private String trackingNo;

    @NotBlank(message = "주문 번호가 유효하지 않습니다.")
    private String orderNo;

}
