package com.nhnacademy.marketgg.server.dto.payment.response;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Size;
import org.springframework.lang.Nullable;

/**
 * 결제 요청에 대해 결제대행사로부터 전달받는 결제 실패 정보입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
public class PaymentFailure {

    @Schema(title = "오류 코드", description = "오류 타입을 보여주는 코드입니다.")
    @Nullable
    private String code;

    @Schema(title = "오류 메시지", description = "오류가 발생한 이유를 표시합니다.")
    @Nullable
    @Size(max = 200)
    private String message;

}
