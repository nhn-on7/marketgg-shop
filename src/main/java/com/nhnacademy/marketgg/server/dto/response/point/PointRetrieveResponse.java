package com.nhnacademy.marketgg.server.dto.response.point;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PointRetrieveResponse {

    private final Long memberId;

    private final Long orderNo;

    private final Integer point;

    private final Integer totalPoint;

    private final String content;

    private final LocalDateTime updatedAt;

}
