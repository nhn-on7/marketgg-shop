package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultMemberServiceTest {

    @InjectMocks
    DefaultMemberService memberService;

    @Mock
    MemberRepository memberRepository;

    private Member member;
    private Member noPassMember;

    @BeforeEach
    void setUp() {
        MemberCreateRequest memberRequest = new MemberCreateRequest();
        member = new Member(memberRequest);
        noPassMember = new Member(memberRequest);

        ReflectionTestUtils.setField(member, "ggpassUpdatedAt",
                                     LocalDateTime.of(2019, 3, 11, 7, 10));
    }

    @Test
    @DisplayName("GG 패스 갱신일 확인")
    void checkPassUpdatedAt() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        LocalDateTime date = memberService.retrievePassUpdatedAt(1L);

        assertThat(date.isBefore(LocalDateTime.now())).isTrue();
    }

    @Test
    @DisplayName("GG 패스 갱신일 null 일시")
    void checkPassUpdatedAtIsNull() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(noPassMember));

        LocalDateTime date = memberService.retrievePassUpdatedAt(1L);

        assertThat(date.getDayOfYear()).isEqualTo(1);
    }

    @Test
    @DisplayName("GG 패스 가입")
    void joinPass() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        memberService.subscribePass(1L);

        assertThat(member.getGgpassUpdatedAt()).isAfter(LocalDateTime.now());
    }

    @Test
    @DisplayName("GG 패스 해지")
    void withdrawPass() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        memberService.withdrawPass(1L);
    }
}