package com.nhnacademy.marketgg.server.repository.deliveryaddress;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.marketgg.server.dto.request.member.MemberGradeCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.repository.cart.CartRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.membergrade.MemberGradeRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
class DeliveryAddressRepositoryTest {

    @Autowired
    DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberGradeRepository memberGradeRepository;

    @Test
    @DisplayName("회원 배송지 목록 조회")
    void testFindDeliveryAddressesByMemberId() {
        Cart cart = cartRepository.save(new Cart());
        MemberGradeCreateRequest memberGradeCreateRequest = new MemberGradeCreateRequest();
        ReflectionTestUtils.setField(memberGradeCreateRequest, "grade", "ThisIsGrade");
        MemberGrade memberGrade = new MemberGrade(memberGradeCreateRequest);

        MemberGrade savedMemberGrade = memberGradeRepository.save(memberGrade);
        Member member = memberRepository.save(Dummy.getDummyMember(cart, savedMemberGrade));

        DeliveryAddress deliveryAddress = new DeliveryAddress(member, Dummy.getDeliveryAddressCreateRequest());

        deliveryAddressRepository.save(deliveryAddress);

        List<DeliveryAddressResponse> deliveryAddressesByMemberId =
                deliveryAddressRepository.findDeliveryAddressesByMemberId(member.getId());

        assertThat(deliveryAddressesByMemberId).isNotEmpty()
                                               .hasSize(1);
    }

}
