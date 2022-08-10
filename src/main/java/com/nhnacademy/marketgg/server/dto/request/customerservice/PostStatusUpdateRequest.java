package com.nhnacademy.marketgg.server.dto.request.customerservice;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 1:1 문의의 상태를 수정하는데 쓰이는 DTO 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@NoArgsConstructor
@Getter
public class PostStatusUpdateRequest {

    @NotBlank
    @Size(min = 1)
    private String status;

}
