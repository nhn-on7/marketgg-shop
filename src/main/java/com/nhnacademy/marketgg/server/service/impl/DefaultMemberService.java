package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.ShopMemberSignUpRequest;
import com.nhnacademy.marketgg.server.dto.response.ShopMemberSignUpResponse;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.membergrade.MemberGradeNotFoundException;
import com.nhnacademy.marketgg.server.repository.deliveryaddress.DeliveryAddressRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.membergrade.MemberGradeRepository;
import com.nhnacademy.marketgg.server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;

    @Override
    public LocalDateTime retrievePassUpdatedAt(final Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        if (Objects.isNull(member.getGgpassUpdatedAt())) {
            return LocalDateTime.of(1, 1, 1, 1, 1, 1);
        }
        return member.getGgpassUpdatedAt();
    }

    @Transactional
    @Override
    public void subscribePass(final Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        member.passSubscribe();

        // TODO : GG PASS 자동결제 로직 필요

        memberRepository.save(member);
    }

    @Override
    public void withdrawPass(final Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);

        // TODO : GG PASS 자동결제 해지 로직 필요

        memberRepository.save(member);
    }

    /**
     * 회원가입시 회원정보를 DB 에 추가하는 메소드입니다.
     *
     * @param signUpRequest - 회원가입시 입력한 정보를 담고있는 객체입니다.
     * @return ShopMemberSignUpResponse - 회원가입을 하는 회원과 추천을 받게되는 회원의 uuid 를 담은 객체 입니다.
     */
    @Transactional
    @Override
    public ShopMemberSignUpResponse signUp(final ShopMemberSignUpRequest signUpRequest) {
        if (referrerCheck(signUpRequest) != null) {
            Member referrerMember = memberRepository.findByUuid(referrerCheck(signUpRequest))
                                                    .orElseThrow(MemberNotFoundException::new);
            return signUp(signUpRequest, referrerMember, registerGrade());
        }
        return new ShopMemberSignUpResponse(memberRepository.save(new Member(signUpRequest, registerGrade())).getId(), null);
    }

    /**
     * 회원가입시 회원에게 부여되는 등급 추가 메소드 입니다.
     *
     * @return MemberGrade - 부여되는 등급 엔티티입니다.
     */
    private MemberGrade registerGrade() {
        return memberGradeRepository.findByGrade("Member")
                                    .orElseThrow(MemberGradeNotFoundException::new);
    }

    /**
     * 추천인이 있을 경우의 추천하는 회원의 회원가입 메소드입니다.
     *
     * @param shopMemberSignupRequest - 회원가입시 입력한 정보를 담고있는 객체입니다.
     * @param referrerMember - 추천을 받은 회원 입니다.
     * @param signupMemberGrade - 회원가입을 하는 회원이 부여 받게되는 등급입니다.
     * @return ShopMemberSignUp - 회원가입을 하는 회원과 추천을 받게되는 회원의 uuid 를 담은 객체 입니다.
     */
    private ShopMemberSignUpResponse signUp(final ShopMemberSignUpRequest shopMemberSignupRequest
            , final Member referrerMember
            , final MemberGrade signupMemberGrade) {

        Member signupMember = memberRepository.save(new Member(shopMemberSignupRequest, signupMemberGrade));
        DeliveryAddress.Pk pk = new DeliveryAddress.Pk(signupMember.getId());
        deliveryAddressRepository.save(new DeliveryAddress(pk, signupMember, shopMemberSignupRequest));
        return new ShopMemberSignUpResponse(signupMember.getId(), referrerMember.getId());
    }

    /**
     * 추천인 여부를 체크하는 메소드 입니다.
     *
     * @param shopMemberSignupRequest - 회원가입시 입력한 정보를 담고있는 객체입니다.
     * @return 추천인의 uuid 를 담고있는 메소드를 반환합니다.
     */
    private String referrerCheck(final ShopMemberSignUpRequest shopMemberSignupRequest) {
        return shopMemberSignupRequest.getReferrerUuid();
    }

}
