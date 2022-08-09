package com.nhnacademy.marketgg.server.dto.request.customerservice;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
public class PostRequest {

    @NotBlank
    @Size(min = 1, max = 6)
    private String categoryCode;

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

    @NotBlank
    @Size(min = 1, max = 100)
    private String content;

    @NotBlank
    @Size(min = 1, max =100)
    private String reason;

}
