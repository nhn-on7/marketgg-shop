package com.nhnacademy.marketgg.server.dto.request;

import com.nhnacademy.marketgg.server.entity.MemberGrade;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원을 생성하기 위한 요청 정보를 담고 있는 클래스입니다.
 *
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class MemberCreateRequest {

    @NotNull
    private MemberGrade memberGrade;

    @NotBlank
    @Size(min = 36, max = 36)
    private String uuid;

    @NotNull
    @Size(min = 1, max = 1)
    private Character gender;

    private LocalDate birthDate;

    private LocalDateTime ggpassUpdateAt;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

}
