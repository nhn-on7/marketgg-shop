package com.nhnacademy.marketgg.server.service.member;

import static com.nhnacademy.marketgg.server.constant.CouponsName.SIGNUP;
import static com.nhnacademy.marketgg.server.constant.PointContent.REFERRED;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.request.member.MemberWithdrawRequest;
import com.nhnacademy.marketgg.server.dto.request.member.SignupRequest;
import com.nhnacademy.marketgg.server.dto.response.member.MemberResponse;
import com.nhnacademy.marketgg.server.dto.response.member.SignupResponse;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.eventlistener.event.givencoupon.SignedUpEvent;
import com.nhnacademy.marketgg.server.eventlistener.event.savepoint.RecommendEvent;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.membergrade.MemberGradeNotFoundException;
import com.nhnacademy.marketgg.server.repository.auth.AuthRepository;
import com.nhnacademy.marketgg.server.repository.cart.CartRepository;
import com.nhnacademy.marketgg.server.repository.deliveryaddress.DeliveryAddressRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.membergrade.MemberGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 회원 서비스의 구현체입니다.
 *
 * @author 박세완
 * @author 김훈민
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final ApplicationEventPublisher publisher;
    private final AuthRepository authRepository;

    @Override
    public MemberResponse retrieveMember(String uuid) {
        Member member = memberRepository.findByUuid(uuid)
                                        .orElseThrow(MemberNotFoundException::new);

        return MemberResponse.builder()
                             .memberGrade(member.getMemberGrade().getGrade())
                             .gender(member.getGender())
                             .birthDay(member.getBirthDate())
                             .build();
    }

    /**
     * 회원가입시 회원정보를 Shop DB 에 추가 및 Auth Server 로 데이터를 전송하는 메소드입니다.
     *
     * @param signUpRequest - 회원가입시 입력한 정보를 담고있는 객체입니다.
     */
    @Transactional
    @Override
    public void signUp(final SignupRequest signUpRequest) throws JsonProcessingException {
        Cart savedCart = cartRepository.save(new Cart());

        ShopResult<SignupResponse> authResponse = authRepository.signup(new SignupRequest(signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getName(),
                signUpRequest.getPhoneNumber(),
                signUpRequest.getReferrerEmail(),
                signUpRequest.getProvider()));

        Member signUpMember = memberRepository.save(new Member(signUpRequest, authResponse.getData()
                                                                                          .getUuid(),
                registerGrade(), savedCart));

        deliveryAddressRepository.save(new DeliveryAddress(signUpMember, signUpRequest));

        publisher.publishEvent(new SignedUpEvent(SIGNUP.couponName(), signUpMember));

        if (Objects.nonNull(authResponse.getData()
                                        .getReferrerUuid())) {

            Member referredMember = memberRepository.findByUuid(authResponse.getData()
                                                                            .getReferrerUuid())
                                                    .orElseThrow(MemberNotFoundException::new);

            publisher.publishEvent(new RecommendEvent(signUpMember, REFERRED.getContent()));
            publisher.publishEvent(new RecommendEvent(referredMember, REFERRED.getContent()));
        }
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

}
