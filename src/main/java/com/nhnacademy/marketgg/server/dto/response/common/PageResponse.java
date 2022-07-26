package com.nhnacademy.marketgg.server.dto.response.common;

import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PageResponse<T> extends CommonResponse {

    private List<T> data;
    private long pageNum;
    private long pageSize;
    private long totalSize;

    public PageResponse(List<T> data, long pageNum, long pageSize, long totalSize) {
        super(true);
        this.data = data;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
    }

}
