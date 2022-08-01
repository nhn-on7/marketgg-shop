package com.nhnacademy.marketgg.server.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문을 생성하기 위한 요청 정보를 담고 있는 클래스입니다.
 *
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class OrderCreateRequest {

    @NotNull
    private Long totalAmount;

    @NotBlank
    @Size(min = 1, max = 10)
    private String orderStatus;

    @NotNull
    private Integer usedPoint;

}
