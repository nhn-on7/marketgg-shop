package com.nhnacademy.marketgg.server.dto.response.common;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ListResponse<T> extends CommonResponse {

    private List<T> data;

    public ListResponse(List<T> data) {
        super(true);
        this.data = data;
    }

}
