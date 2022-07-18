package com.nhnacademy.marketgg.server.dto.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PointHistoryRequest {

    private Integer point;

    private Integer totalPoint;

    private String content;

    private LocalDateTime updatedAt;

}
