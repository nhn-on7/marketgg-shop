package com.nhnacademy.marketgg.server.dto.payment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카드 결제에 대한 응답 결과 객체입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class CardResponse {

    @NotNull
    private Long amount;

    @NotBlank
    @Size(min = 2, max = 10)
    private String company;

    @NotBlank
    @Size(min = 2, max = 10)
    private String number;

    @NotBlank
    @Size(min = 2, max = 10)
    private String cardType;

    @NotBlank
    @Size(min = 2, max = 10)
    private String ownerType;

    @NotBlank
    @Size(min = 2, max = 255)
    private String receiptUrl;

    @NotBlank
    @Size(min = 2, max = 20)
    private String acquireStatus;

}
