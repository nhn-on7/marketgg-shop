package com.nhnacademy.marketgg.server.dto.response;

import com.nhnacademy.marketgg.server.entity.MemberGrade;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class MemberResponse {

    private final MemberGrade memberGrade;
    private final Character gender;
    private final LocalDate birthDay;
    private final LocalDateTime ggpassUpdatedAt;

}
