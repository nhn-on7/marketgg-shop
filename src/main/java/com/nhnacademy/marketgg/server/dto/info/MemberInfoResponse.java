package com.nhnacademy.marketgg.server.dto.info;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 멤버 이름을 받아오기 위한 DTO 입니다.
 *
 * @author 민아영
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class MemberInfoResponse {

    private final String name;
    private final String email;
    private final String phoneNumber;

}

