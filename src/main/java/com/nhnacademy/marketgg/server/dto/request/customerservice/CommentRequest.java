package com.nhnacademy.marketgg.server.dto.request.customerservice;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
public class CommentRequest {

    @NotBlank
    @Size(min = 1, max = 255)
    private String content;

}
