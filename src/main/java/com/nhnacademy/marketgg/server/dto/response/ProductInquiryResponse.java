package com.nhnacademy.marketgg.server.dto.response;

import com.nhnacademy.marketgg.server.entity.Member;

import java.time.LocalDateTime;

public interface ProductInquiryResponse {

    Member getMember();

    String getTitle();

    String getContent();

    Boolean getIsSecret();

    String getAdminReply();

    LocalDateTime getCreatedAt();

}
