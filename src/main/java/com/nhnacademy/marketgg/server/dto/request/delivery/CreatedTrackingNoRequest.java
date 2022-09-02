package com.nhnacademy.marketgg.server.dto.request.delivery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatedTrackingNoRequest {

    @NotBlank(message = "운송장 번호가 유효하지 않습니다.")
    @Size(min = 36, max = 36)
    private String trackingNo;

    @NotBlank(message = "주문 번호가 유효하지 않습니다.")
    private String orderNo;

}
