package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import java.util.Objects;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    /**
     * 회원의 식별번호입니다.
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    private Long memberNo;

    /**
     * 회원이 속한 회원등급입니다.
     *
     * @since 1.0.0
     */
    @ManyToOne
    @JoinColumn(name = "member_grade_no")
    private MemberGrade memberGrade;

    /**
     * 회원의 이메일입니다.
     *
     * @since 1.0.0
     */
    @Column
    private String email;

    /**
     * 회원의 성별입니다. (남자 = M, 여자 = F)
     *
     * @since 1.0.0
     */
    @Column
    private Character Gender;

    /**
     * 회원의 생일입니다.입력받지 않을 수 있습니다.
     *
     * @since 1.0.0
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * 회원의 GG 패스 갱신일시입니다. 입력받지 않을 수 있습니다.
     *
     * @since 1.0.0
     */
    @Column(name = "ggpass_updated_at")
    private LocalDateTime ggpassUpdatedAt;

    /**
     * 회원의 가입일시입니다.
     *
     * @since 1.0.0
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 회원의 정보수정일시입니다.
     *
     * @since 1.0.0
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 회원의 탈퇴일시입니다. 입력받지 않을 수 있습니다.
     *
     * @since 1.0.0
     */
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
        Gender = memberRequest.getGender();
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

}
