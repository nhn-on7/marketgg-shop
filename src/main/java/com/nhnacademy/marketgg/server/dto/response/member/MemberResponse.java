package com.nhnacademy.marketgg.server.dto.response.member;

import com.nhnacademy.marketgg.server.dto.info.AuthInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Builder
@Getter
@ToString
public class MemberResponse {

    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String memberGrade;
    private final Character gender;
    private final LocalDate birthDay;

    public MemberResponse(AuthInfo authInfo, MemberInfo memberInfo) {
        this.email = authInfo.getEmail();
        this.name = authInfo.getName();
        this.phoneNumber = authInfo.getPhoneNumber();
        this.memberGrade = memberInfo.getMemberGrade();
        this.gender = memberInfo.getGender();
        this.birthDay = memberInfo.getBirthDate();
    }

}
