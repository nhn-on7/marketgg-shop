package com.nhnacademy.marketgg.server.dto.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PointHistoryRequest {

    private static final Integer VIP = 3;
    private static final Integer GVIP = 5;

    private Integer point;

    private String content;

    private LocalDateTime updatedAt;

    public void isVip() {
        this.point = point * VIP;
    }

    public void isGVip() {
        this.point = point * GVIP;
    }

}
