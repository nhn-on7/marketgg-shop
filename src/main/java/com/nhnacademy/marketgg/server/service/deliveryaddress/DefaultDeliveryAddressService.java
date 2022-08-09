package com.nhnacademy.marketgg.server.service.deliveryaddress;

import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.CreateDeliveryAddressRequest;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.UpdateDeliveryAddressRequest;
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

@Service
@RequiredArgsConstructor
public class DefaultDeliveryAddressService implements DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;
    private final MemberRepository memberRepository;

    @Override
    public void createDeliveryAddress(final MemberInfo memberInfo,
                                      final CreateDeliveryAddressRequest deliveryAddressRequest) {

        Member member = getMember(memberInfo);

        DeliveryAddress.Pk pk = new DeliveryAddress.Pk(member.getId());

        DeliveryAddress deliveryAddress = new DeliveryAddress(pk,
                member,
                deliveryAddressRequest.isDefaultAddress(),
                deliveryAddressRequest.getZipCode(),
                deliveryAddressRequest.getAddress(),
                deliveryAddressRequest.getDetailAddress());

        deliveryAddressRepository.save(deliveryAddress);

    }

    @Override
    public void updateDeliveryAddress(final MemberInfo memberInfo,
                                      final UpdateDeliveryAddressRequest updateDeliveryAddressRequest) {

        Member member = getMember(memberInfo);

        DeliveryAddress.Pk pk = new DeliveryAddress.Pk(member.getId());

        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(pk)
                                                                   .orElseThrow(DeliveryAddressNotFoundException::new);

        deliveryAddress.update(member, updateDeliveryAddressRequest);
    }

    @Override
    public void deleteDeliveryAddress(final MemberInfo memberInfo) {
        DeliveryAddress.Pk pk = new DeliveryAddress.Pk(getMember(memberInfo).getId());

        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(pk)
                                                                   .orElseThrow(DeliveryAddressNotFoundException::new);

        deliveryAddressRepository.delete(deliveryAddress);
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
