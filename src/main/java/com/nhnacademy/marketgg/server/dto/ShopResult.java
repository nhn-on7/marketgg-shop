package com.nhnacademy.marketgg.server.dto;

import com.nhnacademy.marketgg.server.dto.response.common.ErrorEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Market GG 서비스에서 요청에 대한 공통 응답 결과를 제공하는 클래스입니다.
 *
 * @param <T> - 요청에 대한 응답 데이터 타입
 */
@RequiredArgsConstructor
@Getter
public class ShopResult<T> {

    private final boolean success;

    private final T data;

    private final ErrorEntity error;

    /**
     * 주로 반환형이 Void 일 때 사용
     */
    public static <T> ShopResult<T> success() {
        return new ShopResult<>(true, null, null);
    }

    /**
     * 페이징 정보가 필요한 성공 응답을 내려줄 때 사용
     * 만약 페이징 정보가 필요한 경우 T 타입에 PageEntity<T> 로 설정
     */
    public static <T> ShopResult<T> success(T data) {
        return new ShopResult<>(true, data, null);
    }

    public static <T> ShopResult<T> error(ErrorEntity error) {
        return new ShopResult<>(false, null, error);
    }

}
