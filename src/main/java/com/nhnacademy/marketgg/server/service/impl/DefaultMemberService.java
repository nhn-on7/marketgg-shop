package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.MemberWithdrawRequest;
import com.nhnacademy.marketgg.server.dto.request.ShopMemberSignUpRequest;
import com.nhnacademy.marketgg.server.dto.response.MemberResponse;
import com.nhnacademy.marketgg.server.dto.response.ShopMemberSignUpResponse;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.membergrade.MemberGradeNotFoundException;
import com.nhnacademy.marketgg.server.repository.cart.CartRepository;
import com.nhnacademy.marketgg.server.repository.deliveryaddress.DeliveryAddressRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.membergrade.MemberGradeRepository;
import com.nhnacademy.marketgg.server.service.MemberService;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
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
    public MemberResponse retrieveMember(String uuid) {
        Member member = memberRepository.findByUuid(uuid)
                                        .orElseThrow(MemberNotFoundException::new);

        return MemberResponse.builder()
                             .memberGrade(member.getMemberGrade().getGrade())
                             .gender(member.getGender())
                             .birthDay(member.getBirthDate())
                             .ggpassUpdatedAt(member.getGgpassUpdatedAt())
                             .build();
    }

    @Transactional
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
        Cart savedCart = cartRepository.save(new Cart());
        return new ShopMemberSignUpResponse(memberRepository.save(new Member(signUpRequest, registerGrade(), savedCart))
                                                            .getId(), null);
    }

    /**
     * 회원탈퇴시 SoftDelete 를 위한 메소드입니다.
     *
     * @param uuid                  - 탈퇴를 신청한 회원의 uuid 입니다.
     * @param memberWithdrawRequest - 탈퇴 신청 시간을 담은 객체입니다.
     */
    @Transactional
    @Override
    public void withdraw(final String uuid, final MemberWithdrawRequest memberWithdrawRequest) {
        Member member = memberRepository.findByUuid(uuid)
                                        .orElseThrow(MemberNotFoundException::new);
        member.withdraw(memberWithdrawRequest);
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
     * @param shopMemberSignUpRequest - 회원가입시 입력한 정보를 담고있는 객체입니다.
     * @param referrerMember          - 추천을 받은 회원 입니다.
     * @param signUpMemberGrade       - 회원가입을 하는 회원이 부여 받게되는 등급입니다.
     * @return ShopMemberSignUp - 회원가입을 하는 회원과 추천을 받게되는 회원의 uuid 를 담은 객체 입니다.
     */
    private ShopMemberSignUpResponse signUp(final ShopMemberSignUpRequest shopMemberSignUpRequest,
                                            final Member referrerMember, final MemberGrade signUpMemberGrade) {

        Cart savedCart = cartRepository.save(new Cart());
        Member signUpMember = memberRepository.save(new Member(shopMemberSignUpRequest, signUpMemberGrade, savedCart));
        DeliveryAddress.Pk pk = new DeliveryAddress.Pk(signUpMember.getId());
        deliveryAddressRepository.save(new DeliveryAddress(pk, signUpMember, shopMemberSignUpRequest));
        return new ShopMemberSignUpResponse(signUpMember.getId(), referrerMember.getId());
    }

    /**
     * 추천인 여부를 체크하는 메소드 입니다.
     *
     * @param shopMemberSignUpRequest - 회원가입시 입력한 정보를 담고있는 객체입니다.
     * @return 추천인의 uuid 를 담고있는 메소드를 반환합니다.
     */
    private String referrerCheck(final ShopMemberSignUpRequest shopMemberSignUpRequest) {
        return shopMemberSignUpRequest.getReferrerUuid();
    }

}
