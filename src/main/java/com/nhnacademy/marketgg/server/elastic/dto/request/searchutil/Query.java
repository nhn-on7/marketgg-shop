package com.nhnacademy.marketgg.server.elastic.dto.request.searchutil;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 일반적인 쿼리 질의어를 지정할 수 있습니다..
 *
 * @author 박세완
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public class Query {

    private List<MultiMatch> multi_match;

}
