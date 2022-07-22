package com.nhnacademy.marketgg.server.dto.request;

import com.nhnacademy.marketgg.server.entity.MemberGrade;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberCreateRequest {

    private MemberGrade memberGrade;

    private String uuid;

    private Character gender;

    private LocalDate birthDate;

    private LocalDateTime ggpassUpdateAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

}
