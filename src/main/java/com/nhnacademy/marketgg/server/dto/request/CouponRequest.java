package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CouponRequest {

    private String name;

    private String type;

    private LocalDateTime expiredAt;

    private Integer minimumMoney;

}
