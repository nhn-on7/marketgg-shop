package com.nhnacademy.marketgg.server.dto.payment.response;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 발행한 영수증 정보입니다.
 *
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
public class Receipt {

    @Schema(title = "영수증 정보", description = "발행된 영수증 정보가 담긴 URL 입니다.",
            example = "https://dashboard.tosspayments.com/sales-slip?transactionId=duOfMjWI%2BxoZzSJ5VRibnUDEDnIUwV4tHtum0QzTA5nD1wiIrfzZXB5kXkjm4VD5&ref=PX")
    @NotNull
    private String url;

}
