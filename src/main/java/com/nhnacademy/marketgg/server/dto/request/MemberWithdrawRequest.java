package com.nhnacademy.marketgg.server.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 회원 탈퇴 처리를 위한 요청 정보를 담고 있는 클래스입니다.
 *
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class MemberWithdrawRequest {

    @NotNull
    private LocalDateTime deletedDate;

}
