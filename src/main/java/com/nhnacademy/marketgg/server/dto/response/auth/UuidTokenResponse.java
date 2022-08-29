package com.nhnacademy.marketgg.server.dto.response.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class UuidTokenResponse {

    private final String jwt;
    private final LocalDateTime expiredDate;
    private final String updatedUuid;

}