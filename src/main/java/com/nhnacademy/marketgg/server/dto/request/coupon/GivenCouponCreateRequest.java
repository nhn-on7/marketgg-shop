package com.nhnacademy.marketgg.server.dto.request.coupon;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지급 쿠폰의 요청 DTO 입니다.
 *
 * @version 1.0.0
 */
@NoArgsConstructor
@Getter
public class GivenCouponCreateRequest {

    @NotBlank(message = "쿠폰 이름이 유효하지 않습니다.")
    private String name;

}
