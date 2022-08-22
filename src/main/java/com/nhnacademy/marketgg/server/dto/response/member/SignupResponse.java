package com.nhnacademy.marketgg.server.dto.response.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SignupResponse {

    private final String uuid;
    private final String referrerUuid;

}
