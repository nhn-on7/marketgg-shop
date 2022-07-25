package com.nhnacademy.marketgg.server.dto.response;

import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
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
public class CustomerServiceCommentDto {

    private Long id;

    private CustomerServicePost csPost;

    private Member member;

    private String content;

    private LocalDateTime createdAt;

}
