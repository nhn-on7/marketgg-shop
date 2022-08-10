package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.member.MemberGradeCreateRequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 등급 개체입니다.
 *
 * @author 공통
 * @version 1.0
 * @since 1.0
 */
@Table(name = "member_grades")
@Entity
@NoArgsConstructor
@Getter
public class MemberGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_grade_no")
    private Long id;

    @Column
    @NotBlank
    @Size(min = 6, max = 20)
    private String grade;

    public MemberGrade(MemberGradeCreateRequest memberGradeCreateRequest) {
        this.grade = memberGradeCreateRequest.getGrade();
    }

}
