package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * 토큰을 응답합니다.
 */
@RequiredArgsConstructor
@Getter
public class TokenResponse {

    private final String jwt;
    private final LocalDateTime expiredDate;

}
