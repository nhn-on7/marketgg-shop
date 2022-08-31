package com.nhnacademy.marketgg.server.dto.info;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberInfoRequest {

    @NotNull
    @NotBlank
    private final String uuid;

}
