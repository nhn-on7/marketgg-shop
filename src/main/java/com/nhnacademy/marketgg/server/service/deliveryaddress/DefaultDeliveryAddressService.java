package com.nhnacademy.marketgg.server.service.deliveryaddress;

import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.deliveryaddresses.DeliveryAddressNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.deliveryaddress.DeliveryAddressRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultDeliveryAddressService implements DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;
    private final MemberRepository memberRepository;

    @Override
    public void createDeliveryAddress(final MemberInfo memberInfo,
                                      final DeliveryAddressCreateRequest deliveryAddressRequest) {

        Member member = getMember(memberInfo);

        DeliveryAddress deliveryAddress = new DeliveryAddress(member, deliveryAddressRequest);

        deliveryAddressRepository.save(deliveryAddress);
    }

    @Override
    public void updateDeliveryAddress(final MemberInfo memberInfo,
                                      final DeliveryAddressUpdateRequest deliveryAddressUpdateRequest) {

        Member member = getMember(memberInfo);

        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(deliveryAddressUpdateRequest.getId())
                                                                   .orElseThrow(DeliveryAddressNotFoundException::new);

        deliveryAddress.update(member, deliveryAddressUpdateRequest);
    }

    @Override
    public void deleteDeliveryAddress(final MemberInfo memberInfo,
                                      final Long delivery_no) {

        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(delivery_no)
                                                                   .orElseThrow(DeliveryAddressNotFoundException::new);

        if (Objects.equals(deliveryAddress.getMember().getId(), getMember(memberInfo).getId())) {
            deliveryAddressRepository.delete(deliveryAddress);
        }

    }

    @Override
    public List<DeliveryAddressResponse> retrieveDeliveryAddresses(final MemberInfo memberInfo) {
        return deliveryAddressRepository.findDeliveryAddressesByMemberId(getMember(memberInfo).getId());
    }

    private Member getMember(MemberInfo memberInfo) {
        return memberRepository.findById(memberInfo.getId())
                               .orElseThrow(MemberNotFoundException::new);
    }

}
