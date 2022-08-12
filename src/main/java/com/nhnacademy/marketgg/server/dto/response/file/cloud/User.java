package com.nhnacademy.marketgg.server.dto.response.file.cloud;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 오브젝트 스토리지에 토큰 요청을 했을 경우 받는 응답객체입니다.
 * 응답 값이 매우 많기 때문에, 필요한 값만 받기 위해 @JsonIgnoreProperties를 달았습니다.
 *
 * @author 조현진
 */
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String id;

}
