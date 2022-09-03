package com.nhnacademy.marketgg.server.dto.response.point;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(name = "회원 번호", description = "회원의 식별번호입니다.", example = "1")
    private final Long memberId;

    @Schema(name = "포인트", description = "포인트의 값입니다.", example = "1000")
    private final Integer point;

    @Schema(name = "누적포인트", description = "누적된 포인트의 값입니다.", example = "3000")
    private final Integer totalPoint;

    @Schema(name = "적립/사용 내용", description = "포인트 적립/사용의 내용입니다.", example = "구매")
    private final String content;

    @Schema(name = "내역일시", description = "적립/사용된 일시입니다.", example = "2020-07-10T15:00:00.000")
    private final LocalDateTime updatedAt;

}
