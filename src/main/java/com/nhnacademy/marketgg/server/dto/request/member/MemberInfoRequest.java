package com.nhnacademy.marketgg.server.dto.request.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter
public class MemberInfoRequest {

    @NotNull
    @NotBlank
    private final String uuid;

}
