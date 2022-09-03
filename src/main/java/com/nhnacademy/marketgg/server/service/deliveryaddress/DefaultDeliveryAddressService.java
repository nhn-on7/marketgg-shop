package com.nhnacademy.marketgg.server.service.deliveryaddress;

import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.deliveryaddresses.DeliveryAddressNotFoundException;
import com.nhnacademy.marketgg.server.exception.deliveryaddresses.MinimumDeliveryAddressCountException;
import com.nhnacademy.marketgg.server.exception.deliveryaddresses.OverDeliveryAddressCountException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.deliveryaddress.DeliveryAddressRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 배송지 관리 Service 구현체 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DefaultDeliveryAddressService implements DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;
    private final MemberRepository memberRepository;

    /**
     * 회원이 회원가입 외 추가적으로 배송지를 등록하는 메소드 입니다.
     *
     * @param memberInfo             - 배송지를 등록하는 회원의 정보입니다.
     * @param deliveryAddressRequest - 추가할 배송지를 담은 DTO 객체 입니다.
     */
    @Transactional
    @Override
    public void createDeliveryAddress(final MemberInfo memberInfo,
                                      final DeliveryAddressCreateRequest deliveryAddressRequest) {
        Member member = getMember(memberInfo);

        Integer totalCount = deliveryAddressRepository.countMemberAddresses(member);
        if (totalCount >= 3) {
            throw new OverDeliveryAddressCountException();
        }

        DeliveryAddress deliveryAddress = new DeliveryAddress(member, deliveryAddressRequest);

        DeliveryAddress defaultAddress = deliveryAddressRepository.existsDefaultDeliveryAddress(member);

        if (Objects.nonNull(defaultAddress)) {
            defaultAddress.convertIsDefaultAddress();
        }

        deliveryAddressRepository.save(deliveryAddress);
    }

    /**
     * 회원이 배송지를 삭제할 때 사용하는 메소드 입니다.
     *
     * @param memberInfo        - 배송지를 삭제하는 회원의 정보입니다.
     * @param deliveryAddressId - 삭제할 배송지의 번호 입니다.
     */
    @Transactional
    @Override
    public void deleteDeliveryAddress(final MemberInfo memberInfo,
                                      final Long deliveryAddressId) {

        Member member = getMember(memberInfo);

        Integer minimumCount = deliveryAddressRepository.countMemberAddresses(member);
        if (minimumCount <= 1) {
            throw new MinimumDeliveryAddressCountException();
        }

        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(deliveryAddressId)
                                                                   .orElseThrow(DeliveryAddressNotFoundException::new);

        if (Objects.equals(deliveryAddress.getMember().getId(), getMember(memberInfo).getId())) {
            deliveryAddressRepository.delete(deliveryAddress);
        }

    }

    /**
     * 회원의 모든 배송지를 보여주는 GetMapping 메소드 입니다.
     *
     * @param memberInfo - 모든 배송지를 조회하는 회원의 정보입니다.
     * @return 조회하는 회원의 모든 배송정보를 담은 List 타입 입니다.
     */
    @Override
    public List<DeliveryAddressResponse> retrieveDeliveryAddresses(final MemberInfo memberInfo) {
        return deliveryAddressRepository.findDeliveryAddressesByMemberId(getMember(memberInfo).getId());
    }

    /**
     * 배송지를 관리하는 회원의 정보 검증하기 위한 메소드 입니다.
     *
     * @param memberInfo - 배송지 관리하는 회원의 정보 입니다.
     * @return 검증된 회원입니다.
     */
    private Member getMember(MemberInfo memberInfo) {
        return memberRepository.findById(memberInfo.getId())
                               .orElseThrow(MemberNotFoundException::new);
    }

}
