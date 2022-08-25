package com.nhnacademy.marketgg.server.dto.request.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Getter
public class MemberUpdateRequest {

    @NotBlank
    private final String password;
    @NotBlank
    private final String name;
    @NotBlank
    private final String phoneNumber;

}

