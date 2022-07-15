package com.nhnacademy.marketgg.server.dto.response;

import java.time.LocalDateTime;

public interface ProductInquiryResponse {

    String getMember();

    String getTitle();

    String getContent();

    Boolean getIsSecret();

    String getAdminReply();

    LocalDateTime getCreatedAt();

}
