package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ShopMemberSignupResponse {

    private final Long signupMemberId;
    private final Long referrerMemberId;
}
