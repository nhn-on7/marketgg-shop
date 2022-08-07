package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.member.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberWithdrawRequest;
import com.nhnacademy.marketgg.server.dto.request.member.ShopMemberSignUpRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 개체입니다.
 *
 * @version 1.0
 * @since 1.0
 */
@Table(name = "members")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
// @ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_grade_no")
    private MemberGrade memberGrade;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_no")
    private Cart cart;

    @NotBlank
    @Size(min = 36, max = 36)
    @Column(unique = true)
    private String uuid;

    @NotNull
    @Column
    private Character gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "ggpass_updated_at")
    private LocalDateTime ggpassUpdatedAt;

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @NotNull
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /**
     * 회원을 생성하기 위한 생성자입니다.
     *
     * @param memberRequest - 회원을 생성하기 위한 DTO 입니다.
     * @since 1.0.0
     */
    public Member(final MemberCreateRequest memberRequest, final Cart cart) {
        this.memberGrade = memberRequest.getMemberGrade();
        this.cart = cart;
        this.uuid = memberRequest.getUuid();
        this.gender = memberRequest.getGender();
        this.birthDate = memberRequest.getBirthDate();
        this.ggpassUpdatedAt = memberRequest.getGgpassUpdateAt();
        this.createdAt = memberRequest.getCreatedAt();
        this.updatedAt = memberRequest.getUpdatedAt();
        this.deletedAt = memberRequest.getDeletedAt();
    }

    /**
     * 회원가입 처리를 위한 생성자 입니다.
     *
     * @param shopMemberSignupRequest - 클라이언트 폼에서 가입한 회원의 정보를 담은 DTO 입니다.
     * @param signUpMemberGrade       - 회원가입시 부여될 등급을 담은 객체입니다.
     * @since 1.0.0
     */
    public Member(final ShopMemberSignUpRequest shopMemberSignupRequest, final MemberGrade signUpMemberGrade,
                  final Cart cart) {

        this.memberGrade = signUpMemberGrade;
        this.cart = cart;
        this.uuid = shopMemberSignupRequest.getUuid();
        this.gender = shopMemberSignupRequest.getGender();
        this.birthDate = shopMemberSignupRequest.getBirthDate();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
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

    public void withdraw(final MemberWithdrawRequest memberWithdrawRequest) {
        this.deletedAt = memberWithdrawRequest.getDeletedAt();
    }

    public void updateGrade(MemberGrade memberGrade) {
        this.memberGrade = memberGrade;
    }
}
