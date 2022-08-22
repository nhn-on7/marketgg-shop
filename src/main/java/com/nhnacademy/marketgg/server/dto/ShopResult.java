package com.nhnacademy.marketgg.server.dto;

import com.nhnacademy.marketgg.server.dto.payment.PaymentResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Market GG 서비스에서 요청에 대한 공통 응답 결과를 제공하는 클래스입니다.
 *
 * @param <T> - 요청에 대한 응답 데이터 타입
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
public class ShopResult<T> {

    @Schema(title = "응답 성공 여부", description = "API 요청 시 응답 결과의 성공 반환 여부를 판단하는 flag 값입니다.", example = "true")
    private final boolean success;

    @Schema(title = "응답 결과 데이터", description = "API 요청에 대한 응답 결과 데이터입니다.",
            anyOf = { PaymentResponse.class })
    private final T data;

    private final ErrorEntity error;

    /**
     * 주로 반환형이 Void 일 때 사용
     */
    public static ShopResult<String> successWithDefaultMessage() {
        return new ShopResult<>(true, "data successfully", null);
    }

    /**
     * 페이징 정보가 필요한 성공 응답을 내려줄 때 사용
     * 만약 페이징 정보가 필요한 경우 T 타입에 PageEntity<T> 로 설정
     */
    public static <T> ShopResult<T> successWith(T data) {
        return new ShopResult<>(true, data, null);
    }

    public static <T> ShopResult<T> error(ErrorEntity error) {
        return new ShopResult<>(false, null, error);
    }

}
