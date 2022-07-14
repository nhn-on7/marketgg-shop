package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.MemberGradeCreateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_grade_no")
    private Long memberGradeNo;

    @Column
    private String grade;

    // TODO: DibServieTest 를 위해 임시로 작성한 코드입니다. 추후 수정하거나 삭제해주세요!
    public MemberGrade(MemberGradeCreateRequest memberGradeCreateRequest) {

    }

}
