package com.nhnacademy.marketgg.server.dto.response.temp;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PageListResponse<T> extends CommonResponse {

    private List<T> data;
    private long pageNum;
    private long pageSize;
    private long totalSize;

    public PageListResponse(List<T> data, long pageNum, long pageSize, long totalSize) {
        super(true);
        this.data = data;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
    }

}
