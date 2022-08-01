package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostRequest {

    private String categoryCode;

    private String title;

    private String content;

    private String reason;

}
