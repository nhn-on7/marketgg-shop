package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberGradeCreateRequest;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.repository.deliveryaddress.DeliveryAddressRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.deliveryaddress.DefaultDeliveryAddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultDeliveryAddressServiceTest {

    @InjectMocks
    DefaultDeliveryAddressService deliveryAddressService;

    @Mock
    DeliveryAddressRepository deliveryAddressRepository;

    @Mock
    MemberRepository memberRepository;

    private final static Long CART_NO = 1L;
    private final static Long MEMBER_NO = 1L;

    @Test
    @DisplayName("배송지 추가")
    void testCreateDeliveryAddress() {

        Cart dummyCart = Dummy.getDummyCart(CART_NO);
        MemberGradeCreateRequest memberGradeCreateRequest = new MemberGradeCreateRequest();
        ReflectionTestUtils.setField(memberGradeCreateRequest, "grade", "ThisIsGrade");
        MemberGrade memberGrade = new MemberGrade(memberGradeCreateRequest);
        Member dummyMember = Dummy.getDummyMember(dummyCart, memberGrade);

        MemberInfo dummyMemberInfo = Dummy.getDummyMemberInfo(MEMBER_NO, dummyCart);
        DeliveryAddressCreateRequest deliveryAddressCreateRequest = Dummy.getDeliveryAddressCreateRequest();

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(dummyMember));

        deliveryAddressService.createDeliveryAddress(dummyMemberInfo, deliveryAddressCreateRequest);

        then(deliveryAddressRepository).should(times(1)).save(any(DeliveryAddress.class));
    }

}
