package com.nhnacademy.marketgg.server.service.deliveryaddress;

import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;

import java.util.List;

public interface DeliveryAddressService {

    void createDeliveryAddress(final MemberInfo memberInfo,
                               final DeliveryAddressCreateRequest deliveryAddressRequest);


    void updateDeliveryAddress(final MemberInfo memberInfo,
                               final DeliveryAddressUpdateRequest deliveryAddressUpdateRequest);

    void deleteDeliveryAddress(final MemberInfo memberInfo);

    List<DeliveryAddressResponse> retrieveDeliveryAddresses(final MemberInfo member);
}
