package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class GivenCouponRequest {

    @NotBlank(message = "쿠폰 이름이 유효하지 않습니다.")
    private String name;

}
