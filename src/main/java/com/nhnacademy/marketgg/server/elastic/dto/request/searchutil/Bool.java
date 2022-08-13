package com.nhnacademy.marketgg.server.elastic.dto.request.searchutil;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 검색 조건을 선언 할 수 있습니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public class Bool {

    private Must must;

}
