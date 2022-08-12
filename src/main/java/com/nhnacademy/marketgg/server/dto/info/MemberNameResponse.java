package com.nhnacademy.marketgg.server.dto.info;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 멤버 이름을 받아오기 위한 DTO 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class MemberNameResponse {

    private final String uuid;
    private final String name;

}
