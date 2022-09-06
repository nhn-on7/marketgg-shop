package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.response.member.AdminAuthResponse;
import com.nhnacademy.marketgg.server.dto.response.member.AdminMemberResponse;
import com.nhnacademy.marketgg.server.dto.response.member.MemberResponse;
import com.nhnacademy.marketgg.dummy.Dummy;
import com.nhnacademy.marketgg.dummy.DummyPageEntity;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.auth.AuthRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.member.DefaultMemberService;
import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultMemberServiceTest {

    @InjectMocks
    DefaultMemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    AuthRepository authRepository;

    @Test
    @DisplayName("UUID 로 회원 조회")
    void testRetrieveMemberByUuid() throws Exception {
        String uuid = "UUID";

        Class<Member> memberClass = Member.class;
        Constructor<?> constructor = memberClass.getDeclaredConstructor();
        constructor.setAccessible(true);

        Member member = Dummy.getDummyMember(uuid, new Cart());
        given(memberRepository.findByUuid(uuid)).willReturn(Optional.of(member));

        MemberResponse memberResponse = memberService.retrieveMember(uuid);

        assertThat(memberResponse).isNotNull();
        then(memberRepository).should(times(1)).findByUuid(uuid);
    }

    @Test
    @DisplayName("UUID 로 회원 조회 실패")
    void testRetrieveMemberByUuidFail() {
        String uuid = "UUID";

        given(memberRepository.findByUuid(uuid)).willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.retrieveMember(uuid))
            .isInstanceOf(MemberNotFoundException.class);

        then(memberRepository).should(times(1)).findByUuid(uuid);
    }

    @Test
    @DisplayName("관리자의 사용자 목록 조회")
    void testRetrieveMembers() throws JsonProcessingException {

        MemberInfo memberInfo = new MemberInfo(1L, new Cart(), "VIP", 'M', LocalDate.now());

        given(authRepository.retrieveMemberList(anyString(), any(Pageable.class))).willReturn(this.getDummyData());
        given(memberRepository.findMemberInfoByUuid(anyString())).willReturn(Optional.of(memberInfo));
        String jwt = "jwt";
        Pageable pageable = PageRequest.of(0, 10);

        PageEntity<AdminMemberResponse> pageEntity = memberService.retrieveMembers(jwt, pageable);

        then(authRepository).should(times(1)).retrieveMemberList(anyString(), any(Pageable.class));
        then(memberRepository).should(times(10)).findMemberInfoByUuid(anyString());

        assertThat(pageEntity).isNotNull();
    }

    private PageEntity<AdminAuthResponse> getDummyData() throws JsonProcessingException {
        String data = DummyPageEntity.getDummyData();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        DummyPageEntity<AdminAuthResponse> result = objectMapper.readValue(data, new TypeReference<>() {
        });

        return result.getPageEntity();
    }

}
