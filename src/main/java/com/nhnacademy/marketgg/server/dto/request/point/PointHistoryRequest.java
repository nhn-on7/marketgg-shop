package com.nhnacademy.marketgg.server.dto.request.point;

import static com.nhnacademy.marketgg.server.constant.MemberBenefits.G_VIP;
import static com.nhnacademy.marketgg.server.constant.MemberBenefits.VIP;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포인트 사용 내역을 생성하기 위한 요청 정보를 담고 있는 클래스입니다.
 *
 * @author 박세완
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PointHistoryRequest {

    @Schema(name = "포인트", description = "적립/사용 된 포인트 정보입니다.", example = "100")
    @NotNull
    private Integer point;

    @Schema(name = "포인트 적립/사용 내용", description = "적립/사용 된 내역정보입니다.", example = "구매")
    @NotNull
    @Size(min = 1, max = 100)
    private String content;

}
