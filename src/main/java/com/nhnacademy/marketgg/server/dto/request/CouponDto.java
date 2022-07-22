package com.nhnacademy.marketgg.server.dto.request;

import lombok.Builder;
import lombok.Getter;

// MEMO 2: Builder 로 Dto 에서 Entity 로 변환하는 생성자 MapStruct 가 만들어준당
@Builder
@Getter
// MEMO 3: Request 와 Response 를 따로 안두고 Dto 하나도 수정!
public class CouponDto {

    private final Long id;

    private final String name;

    private final String type;

    private final Integer expiredDate;

    private final Integer minimumMoney;

    private final Double discountAmount;

}
