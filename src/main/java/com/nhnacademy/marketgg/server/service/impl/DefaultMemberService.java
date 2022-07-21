package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.ShopMemberSignupRequest;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.membergrade.MemberGradeNotFoundException;
import com.nhnacademy.marketgg.server.exception.pointhistory.PointHistoryNotFoundException;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.membergrade.MemberGradeRepository;
import com.nhnacademy.marketgg.server.repository.pointhistory.PointHistoryRepository;
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

    private final PointHistoryRepository pointHistoryRepository;

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

    @Transactional
    @Override
    public void signup(final ShopMemberSignupRequest shopMemberSignupRequest) {

        final Integer earnPoint = 5000;

        if (getReferrerUuid(shopMemberSignupRequest) != null) {

            Member referrerMember = memberRepository.findByUuid(getReferrerUuid(shopMemberSignupRequest))
                                                    .orElseThrow(MemberNotFoundException::new);

            PointHistory beforeReferrerPointHistory = pointHistoryRepository.findByMember(referrerMember)
                                                                            .orElseThrow(PointHistoryNotFoundException::new);


            PointHistory.earnReferrer(beforeReferrerPointHistory, referrerMember, earnPoint);

            MemberGrade signupMemberGrade = memberGradeRepository.findByGrade("Member")
                                                                 .orElseThrow(MemberGradeNotFoundException::new);

            Member signupMember = memberRepository.save(new Member(shopMemberSignupRequest, signupMemberGrade));

            PointHistory signupMemberPointHistory = pointHistoryRepository.save(new PointHistory(signupMember, 0, 0, "회원가입", LocalDateTime.now()));
            PointHistory.earnReferrer(signupMemberPointHistory, signupMember, earnPoint);
        }

        MemberGrade signupMemberGrade = memberGradeRepository.findByGrade("Member")
                                                             .orElseThrow(MemberGradeNotFoundException::new);

        memberRepository.save(new Member(shopMemberSignupRequest, signupMemberGrade));

    }

    private String getReferrerUuid(ShopMemberSignupRequest shopMemberSignupRequest) {
        return shopMemberSignupRequest.getReferrerUuid();
    }

}
