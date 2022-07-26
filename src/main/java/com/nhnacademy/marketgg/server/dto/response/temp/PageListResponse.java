package com.nhnacademy.marketgg.server.dto.response.temp;

import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class PageListResponse<T> extends CommonResponse {

    private final List<T> data;
    private final Map<String, Integer> page;

    public PageListResponse(List<T> data, Map<String, Integer> page) {
        super(true);
        this.data = data;
        this.page = page;
    }

}
