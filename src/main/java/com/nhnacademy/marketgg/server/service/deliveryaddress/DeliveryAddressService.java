package com.nhnacademy.marketgg.server.service.deliveryaddress;

import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.CreateDeliveryAddressRequest;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.UpdateDeliveryAddressRequest;

public interface DeliveryAddressService {

    void createDeliveryAddress(final MemberInfo memberInfo,
                               final CreateDeliveryAddressRequest deliveryAddressRequest);


    void updateDeliveryAddress(final MemberInfo memberInfo,
                               final UpdateDeliveryAddressRequest updateDeliveryAddressRequest);

}
