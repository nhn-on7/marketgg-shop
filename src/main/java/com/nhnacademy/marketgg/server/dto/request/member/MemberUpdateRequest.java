package com.nhnacademy.marketgg.server.dto.request.member;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

