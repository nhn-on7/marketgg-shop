package com.nhnacademy.marketgg.server.dto.request.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "쿠폰 이름", description = "회원이 등록할 쿠폰의 이름 입니다.", example = "생일 쿠폰")
    @NotBlank(message = "쿠폰 이름이 유효하지 않습니다.")
    private String name;

}
