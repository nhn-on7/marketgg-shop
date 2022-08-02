package com.nhnacademy.marketgg.server.dto.request.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ShopMemberSignUpRequest {

    private Character gender;
    private Boolean isAdmin;
    private LocalDate birthDate;
    private String uuid;
    private String referrerUuid;
    private Integer zipcode;
    private String address;
    private String detailAddress;

}
