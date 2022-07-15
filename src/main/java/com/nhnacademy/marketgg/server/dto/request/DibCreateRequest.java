package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class DibCreateRequest {

    private Long memberNo;

    private Long productNo;

    private String memo;

    private LocalDateTime createdAt;

}
