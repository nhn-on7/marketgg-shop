package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberGradeCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.order.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.dto.response.point.PointRetrieveResponse;
import com.nhnacademy.marketgg.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import com.nhnacademy.marketgg.server.repository.cart.CartRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.pointhistory.PointHistoryRepository;
import com.nhnacademy.marketgg.server.service.point.DefaultPointService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultPointServiceTest {

    @InjectMocks
    DefaultPointService pointService;

    @Mock
    PointHistoryRepository pointHistoryRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    CartRepository cartRepository;

    private PointRetrieveResponse pointRetrieveResponse;
    private PointHistoryRequest pointHistoryRequest;
    private MemberCreateRequest memberCreateRequest;
    private MemberGradeCreateRequest memberGradeCreateRequest;
    private OrderCreateRequest orderCreateRequest;
    private Cart cart;

    @BeforeEach
    void setUp() {
        pointRetrieveResponse =
                new PointRetrieveResponse( 1L, 1000, 1000, "결제", LocalDateTime.now());
        pointHistoryRequest = new PointHistoryRequest();
        memberCreateRequest = new MemberCreateRequest();
        memberGradeCreateRequest = new MemberGradeCreateRequest();
        orderCreateRequest = new OrderCreateRequest();
        cart = cartRepository.save(new Cart());

        ReflectionTestUtils.setField(orderCreateRequest, "paymentType", "신용카드");
    }

    @Test
    @DisplayName("사용자 포인트 내역 목록 조회")
    void testRetrievePointHistories() {
        given(pointHistoryRepository.findAllByMemberId(any(), any())).willReturn(Page.empty());

        pointService.retrievePointHistories(1L, PageRequest.of(0, 10));

        then(pointHistoryRepository).should(times(1)).findAllByMemberId(any(), any());
    }

    @Test
    @DisplayName("관리자의 사용자 포인트 내역 목록 조회")
    void testAdminRetrievePointHistories() {
        given(pointHistoryRepository.findAllForAdmin(any())).willReturn(Page.empty());

        Page<PointRetrieveResponse> responses = pointService.adminRetrievePointHistories(PageRequest.of(0, 10));

        assertThat(responses).isEmpty();
    }

    @Test
    @DisplayName("주문 연관 X 포인트 내역 생성")
    void testCreatePointHistory() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(new Member(memberCreateRequest, cart)));
        ReflectionTestUtils.setField(pointHistoryRequest, "point", 1000);

        pointService.createPointHistory(1L, pointHistoryRequest);

        then(pointHistoryRepository).should(times(1)).save(any(PointHistory.class));
    }

    @ParameterizedTest
    @ValueSource(strings = { "Member", "VIP", "G-VIP" })
    @DisplayName("사용자 등급(일반, VIP, G-VIP)별 주문 관련 포인트 내역 생성")
    void testCreatePointHistoryForOrderIsMember(String value) {
        ReflectionTestUtils.setField(memberGradeCreateRequest, "grade", value);
        MemberGrade memberGrade = new MemberGrade(memberGradeCreateRequest);
        DeliveryAddressCreateRequest deliveryAddressCreateRequest = new DeliveryAddressCreateRequest();

        Member member = new Member(memberCreateRequest, cart);
        DeliveryAddress deliveryAddress = new DeliveryAddress(member, deliveryAddressCreateRequest);
        Order order = Dummy.getDummyOrder();

        ReflectionTestUtils.setField(pointHistoryRequest, "point", 1000);
        ReflectionTestUtils.setField(member, "memberGrade", memberGrade);
        ReflectionTestUtils.setField(member.getMemberGrade(), "grade", value);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        pointService.createPointHistoryForOrder(1L, 1L, pointHistoryRequest);

        then(pointHistoryRepository).should(times(1)).save(any(PointHistory.class));
    }

    @Test
    @DisplayName("사용자 등급 G-VIP, 주문 관련 포인트 마이너스 내역 생성")
    void testCreatePointHistoryForOrderIsMinus() {
        ReflectionTestUtils.setField(memberGradeCreateRequest, "grade", "G-VIP");
        MemberGrade memberGrade = new MemberGrade(memberGradeCreateRequest);
        DeliveryAddressCreateRequest deliveryAddressCreateRequest = new DeliveryAddressCreateRequest();

        Member member = new Member(memberCreateRequest, cart);
        DeliveryAddress deliveryAddress = new DeliveryAddress(member, deliveryAddressCreateRequest);
        Order order = Dummy.getDummyOrder();

        ReflectionTestUtils.setField(pointHistoryRequest, "point", -1000);
        ReflectionTestUtils.setField(member, "memberGrade", memberGrade);
        ReflectionTestUtils.setField(member.getMemberGrade(), "grade", "G-VIP");

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        pointService.createPointHistoryForOrder(1L, 1L, pointHistoryRequest);

        then(pointHistoryRepository).should(times(1)).save(any(PointHistory.class));
    }

}
