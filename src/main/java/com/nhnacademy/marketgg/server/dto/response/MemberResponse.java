package com.nhnacademy.marketgg.server.dto.response;

import com.nhnacademy.marketgg.server.dto.AuthInfo;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class MemberResponse {

    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String memberGrade;
    private final Character gender;
    private final LocalDate birthDay;
    private final LocalDateTime ggpassUpdatedAt;

    public MemberResponse(AuthInfo authInfo, MemberInfo memberInfo) {
        this.email = authInfo.getEmail();
        this.name = authInfo.getName();
        this.phoneNumber = authInfo.getPhoneNumber();
        this.memberGrade = memberInfo.getMemberGrade();
        this.gender = memberInfo.getGender();
        this.birthDay = memberInfo.getBirthDate();
        this.ggpassUpdatedAt = memberInfo.getGgpassUpdatedAt();
    }

}
