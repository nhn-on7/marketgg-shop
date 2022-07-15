package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.MemberGradeCreateRequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "member_grades")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_grade_no")
    private Long id;

    @Column
    private String grade;

    public MemberGrade(MemberGradeCreateRequest memberRequest) {
    }
}
