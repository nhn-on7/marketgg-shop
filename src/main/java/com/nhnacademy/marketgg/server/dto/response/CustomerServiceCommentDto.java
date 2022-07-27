package com.nhnacademy.marketgg.server.dto.response;

import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CustomerServiceCommentDto {

    private Long id;

    private CustomerServicePost customerServicePost;

    private Member member;

    private String content;

    private LocalDateTime createdAt;

}
