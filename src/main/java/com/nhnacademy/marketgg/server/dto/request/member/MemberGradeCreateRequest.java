package com.nhnacademy.marketgg.server.dto.request.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 회원 등급을 생성하기 위한 요청 정보를 담고 있는 클래스입니다.
 *
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class MemberGradeCreateRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String grade;

}
