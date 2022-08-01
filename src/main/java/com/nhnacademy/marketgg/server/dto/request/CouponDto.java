package com.nhnacademy.marketgg.server.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


/**
 * 쿠폰의 요청과 응답의 DTO 입니다.
 *
 * @version 1.0.0
 */
@AllArgsConstructor
// MEMO 2: Builder 로 Dto 에서 Entity 로 변환하는 생성자 MapStruct 가 만들어준당
@Builder
@Getter
@NoArgsConstructor
// MEMO 3: Request 와 Response 를 따로 안두고 Dto 하나도 수정!
public class CouponDto {

    private Long id;

    @NotBlank(message = "쿠폰 이름이 유효하지 않습니다.")
    @Length(min = 3, max = 15)
    private String name;

    @NotBlank
    private String type;

    @NotNull
    @Positive
    private Integer expiredDate;

    @NotNull
    @Positive(message = "최소 주문 금액은 0원이 될 수 없습니다.")
    private Integer minimumMoney;

    @NotNull
    @Positive(message = "할인량은 음수가 될 수 없습니다.")
    private Double discountAmount;

}
