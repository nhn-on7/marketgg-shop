package com.nhnacademy.marketgg.server.dto.request.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 주문 상태를 변경하기 위한 DTO 입니다.
 * 관리자가 수동으로 상태를 변경할 때 필요로 합니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
public class OrderUpdateStatusRequest {

    @Schema(title = "상태", description = "변경할 주문 상태입니다.", example = "배송중")
    @NotNull
    private String status;

}
