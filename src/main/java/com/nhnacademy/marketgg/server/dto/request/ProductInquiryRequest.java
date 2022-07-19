package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductInquiryRequest {

    private Long memberId;
    private String title;
    private String content;
    private Boolean isSecret;
    private String adminReply;

}
