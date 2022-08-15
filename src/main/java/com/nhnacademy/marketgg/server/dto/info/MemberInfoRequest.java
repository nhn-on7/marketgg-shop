package com.nhnacademy.marketgg.server.dto.info;

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
