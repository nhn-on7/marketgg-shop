package com.nhnacademy.marketgg.server.dto.response.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ShopMemberSignUpResponse {

    private final Long signUpMemberId;
    private final Long referrerMemberId;

}
