package com.nhnacademy.marketgg.server.dto.response.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberInfoResponse {

    private final String name;
    private final String email;
    private final String phoneNumber;

}
