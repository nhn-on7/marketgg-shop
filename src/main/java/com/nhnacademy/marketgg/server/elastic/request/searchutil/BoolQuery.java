package com.nhnacademy.marketgg.server.elastic.request.searchutil;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 쿼리에 조건을 지정할 수 있습니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public class BoolQuery {

    private Bool bool;

}
