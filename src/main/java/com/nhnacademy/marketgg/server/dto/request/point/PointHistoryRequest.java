package com.nhnacademy.marketgg.server.dto.request.point;

import static com.nhnacademy.marketgg.server.constant.MemberBenefits.G_VIP;
import static com.nhnacademy.marketgg.server.constant.MemberBenefits.VIP;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포인트 사용 내역을 생성하기 위한 요청 정보를 담고 있는 클래스입니다.
 *
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PointHistoryRequest {

    @NotNull
    private Integer point;

    @NotNull
    @Size(min = 1, max = 100)
    private String content;

    public void vipBenefit() {
        this.point = point * VIP.getBenefit();
    }

    public void gVipBenefit() {
        this.point = point * G_VIP.getBenefit();
    }

}
