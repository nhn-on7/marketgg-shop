package com.nhnacademy.marketgg.server.dto.response.file.cloud;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 오브젝트 스토리지에 토큰 요청을 했을 경우 받는 응답객체입니다.
 *
 * @author 조현진
 */
@NoArgsConstructor
@Getter
@ToString
public class CloudResponse {

    private Access access;
}
