package com.nhnacademy.marketgg.server.dto.response.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class AdminMemberResponse {

    private final String uuid;
    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String memberGrade;
    private final Character gender;
    private final List<String> roles;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime createdAt;

    public AdminMemberResponse(AdminAuthResponse response, MemberInfo memberInfo) {
        this.uuid = response.getUuid();
        this.email = response.getEmail();
        this.name = response.getName();
        this.phoneNumber = response.getPhoneNumber();
        this.memberGrade = memberInfo.getMemberGrade();
        this.gender = memberInfo.getGender();
        this.roles = response.getRoles();
        this.birthDate = memberInfo.getBirthDate();
        this.createdAt = response.getCreatedAt();
    }

}
