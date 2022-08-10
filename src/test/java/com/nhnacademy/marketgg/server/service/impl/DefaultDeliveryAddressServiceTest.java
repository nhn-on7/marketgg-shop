package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.repository.deliveryaddress.DeliveryAddressRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.deliveryaddress.DefaultDeliveryAddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    private final static Long DELIVERY_NO = 1L;

    @Test
    @DisplayName("배송지 추가")
    void testCreateDeliveryAddress() {
        Cart dummyCart = Dummy.getDummyCart(CART_NO);
        Member dummyMember = Dummy.getDummyMember(dummyCart);

        MemberInfo dummyMemberInfo = Dummy.getDummyMemberInfo(MEMBER_NO, dummyCart);
        DeliveryAddressCreateRequest deliveryAddressCreateRequest = Dummy.getDeliveryAddressCreateRequest();

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(dummyMember));

        deliveryAddressService.createDeliveryAddress(dummyMemberInfo, deliveryAddressCreateRequest);

        then(deliveryAddressRepository).should(times(1)).save(any(DeliveryAddress.class));
    }

    @Test
    @DisplayName("배송지 수정")
    void testUpdateDeliveryAddress() {
        Cart dummyCart = Dummy.getDummyCart(CART_NO);
        Member dummyMember = Dummy.getDummyMember(dummyCart);
        MemberInfo dummyMemberInfo = Dummy.getDummyMemberInfo(MEMBER_NO, dummyCart);
        DeliveryAddressUpdateRequest dummyDeliveryAddressUpdateRequest = Dummy.getDeliveryAddressUpdateRequest();

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(dummyMember));
        DeliveryAddress dummyDeliveryAddress = Dummy.getDeliveryAddress();
        given(deliveryAddressRepository.findById(anyLong())).willReturn(Optional.of(dummyDeliveryAddress));
        deliveryAddressService.updateDeliveryAddress(dummyMemberInfo, dummyDeliveryAddressUpdateRequest);

        then(deliveryAddressRepository).should(times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("배송지 삭제")
    void testDeleteDeliveryAddress() {
        Cart dummyCart = Dummy.getDummyCart(CART_NO);
        Member member = Dummy.getDummyMember(dummyCart);

        DeliveryAddress dummyDeliveryAddress = Dummy.getDeliveryAddress();
        given(deliveryAddressRepository.findById(anyLong())).willReturn(Optional.of(dummyDeliveryAddress));
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        deliveryAddressService.deleteDeliveryAddress(Dummy.getDummyMemberInfo(MEMBER_NO, dummyCart), DELIVERY_NO);

        then(deliveryAddressRepository).should(times(1)).delete(any(DeliveryAddress.class));
    }

    @Test
    @DisplayName("배송지 전체 조회")
    void testRetrieveDeliveryAddress() {
        // given
        Cart dummyCart = Dummy.getDummyCart(CART_NO);
        Member dummyMember = Dummy.getDummyMember(dummyCart);
        MemberInfo dummyMemberInfo = Dummy.getDummyMemberInfo(MEMBER_NO, dummyCart);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(dummyMember));
        List<DeliveryAddressResponse> dummyDeliveryAddressResponseList = List.of();
        given(deliveryAddressRepository.findDeliveryAddressesByMemberId(any())).willReturn(dummyDeliveryAddressResponseList);

        // when
        List<DeliveryAddressResponse> deliveryAddressResponseList = deliveryAddressService.retrieveDeliveryAddresses(dummyMemberInfo);

        // then
        then(deliveryAddressRepository).should(times(1)).findDeliveryAddressesByMemberId(any());
        assertThat(deliveryAddressResponseList).isEqualTo(dummyDeliveryAddressResponseList);
    }

}
