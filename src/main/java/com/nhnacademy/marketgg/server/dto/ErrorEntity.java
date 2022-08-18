package com.nhnacademy.marketgg.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Market GG 에서 제공하는 오류에 대한 정보를 담고 있는 클래스입니다.
 *
 * @author 김정민
 * @author 김훈민
 * @author 민아영
 * @author 박세완
 * @author 윤동열
 * @author 이제훈
 * @author 조현진
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public class ErrorEntity {

    @Schema(required = true, title = "오류 메시지", description = "API 요청 도중 오류 발생 시 어떤 오류인지에 대한 메시지가 담겨있습니다.",
            example = "회원 등급을 찾을 수 없습니다.")
    private final String message;

}
