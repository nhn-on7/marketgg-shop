package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor
@Getter
public class EsProductSearchRequest {

    private String keyword;

    private Pageable pageable;

}
