package com.nhnacademy.marketgg.server.dto.request;

import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class CustomerServicePostDto {

    private Long id;

    private Long memberId;

    private String categoryId;

    private String content;

    private String code;

    private String title;

    private String reason;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
