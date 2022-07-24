package com.nhnacademy.marketgg.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// MEMO 2: Builder 로 Dto 에서 Entity 로 변환하는 생성자 MapStruct 가 만들어준당
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
// MEMO 3: Request 와 Response 를 따로 안두고 Dto 하나도 수정!
public class CouponDto {

    private Long id;

    private  String name;

    private String type;

    private Integer expiredDate;

    private Integer minimumMoney;

    private Double discountAmount;

}
