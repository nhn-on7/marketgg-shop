package com.nhnacademy.marketgg.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@Builder
@Getter
public class DefaultPageRequest {

    private int page;
    private int size;

    public DefaultPageRequest() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}
