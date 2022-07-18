package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PointHistoryRequest {

    private static final Integer VIP = 3;
    private static final Integer GVIP = 5;

    private Integer point;

    private String content;

    public void vipBenefit() {
        this.point = point * VIP;
    }

    public void gVipBenefit() {
        this.point = point * GVIP;
    }

}
