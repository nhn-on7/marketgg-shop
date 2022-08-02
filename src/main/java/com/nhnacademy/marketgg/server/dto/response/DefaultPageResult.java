package com.nhnacademy.marketgg.server.dto.response;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * 기본 페이지 응답 결과 객체입니다.
 *
 * @param <D> - 데이터 전송 객체
 * @param <E> - 엔티티
 */
@NoArgsConstructor
@Getter
public class DefaultPageResult<D, E> {

    private List<D> dtoList;

    public DefaultPageResult(Page<E> result, Function<E, D> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
    }

}
