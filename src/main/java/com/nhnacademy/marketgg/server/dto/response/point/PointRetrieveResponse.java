package com.nhnacademy.marketgg.server.dto.response.point;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 포인트 내역을 반환하는 DTO 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
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
