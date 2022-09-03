package com.nhnacademy.marketgg.server.dto.info;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 멤버 이름을 받아오기 위한 DTO 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@NoArgsConstructor
@Getter
public class MemberNameResponse {

    private String uuid;
    private String name;

}
