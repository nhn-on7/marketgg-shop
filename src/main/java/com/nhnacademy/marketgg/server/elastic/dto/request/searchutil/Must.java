package com.nhnacademy.marketgg.server.elastic.dto.request.searchutil;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 반드시 조건에 만족해야 하는 Must 조건 입니다.
 *
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public class Must {

    MultiMatch multi_match;

}
