package com.nhnacademy.marketgg.server.dto.request.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class MemberWithdrawRequest {

    @Email(message = "이메일 양식을 지켜주세요.")
    @NotBlank(message = "이메일을 입력해 주세요.")
    private final String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private final String password;

    private LocalDateTime withdrawAt;

    public void inputWithdrawAt(final LocalDateTime withdrawAt) {
        this.withdrawAt = withdrawAt;
    }

}
