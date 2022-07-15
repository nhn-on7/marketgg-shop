package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 Entity 입니다.
 *
 * @version 1.0.0
 */
@Table(name = "members")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    private Long memberNo;

    @ManyToOne
    @JoinColumn(name = "member_grade_no")
    private MemberGrade memberGrade;

    @Column
    private String email;

    @Column
    private Character gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "ggpass_updated_at")
    private LocalDateTime ggpassUpdatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /**
     * 회원을 생성하기 위한 생성자입니다.
     *
     * @param memberRequest - 회원을 생성하기 위한 DTO 입니다.
     * @since 1.0.0
     */
    public Member(MemberCreateRequest memberRequest) {
        this.memberGrade = memberRequest.getMemberGrade();
        this.email = memberRequest.getEmail();
        this.gender = memberRequest.getGender();
        this.birthDate = memberRequest.getBirthDate();
        this.ggpassUpdatedAt = memberRequest.getGgpassUpdateAt();
        this.createdAt = memberRequest.getCreatedAt();
        this.updatedAt = memberRequest.getUpdatedAt();
        this.deletedAt = memberRequest.getDeletedAt();
    }

    /**
     * 회원이 GG 패스에 구독하기 위한 메소드입니다.
     *
     * @since 1.0.0
     */
    public void passSubscribe() {
        this.ggpassUpdatedAt = (LocalDateTime.now()).plusMonths(1);
    }

    public Member(final MemberCreateRequest memberCreateRequest, final MemberGrade memberGrade) {

    }

}
