package com.nhnacademy.marketgg.server.service.deliveryaddress;

import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;

import java.util.List;

/**
 * 배송지 관리 Service 인터페이스 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
public interface DeliveryAddressService {

    /**
     * 회원이 회원가입 외 추가적으로 배송지를 등록하는 메소드 입니다.
     *
     * @param memberInfo             - 배송지를 등록하는 회원의 정보입니다.
     * @param deliveryAddressRequest - 추가할 배송지를 담은 DTO 객체 입니다.
     */
    void createDeliveryAddress(final MemberInfo memberInfo,
                               final DeliveryAddressCreateRequest deliveryAddressRequest);


    /**
     * 회원이 배송지를 수정할 때 사용하는 메소드 입니다.
     *
     * @param memberInfo                   - 배송지를 수정하는 회원의 정보입니다.
     * @param deliveryAddressUpdateRequest - 수정할 배송지를 담은 DTO 객체 입니다.
     */
    void updateDeliveryAddress(final MemberInfo memberInfo,
                               final DeliveryAddressUpdateRequest deliveryAddressUpdateRequest);

    /**
     * 회원이 배송지를 삭제할 때 사용하는 메소드 입니다.
     *
     * @param memberInfo        - 배송지를 삭제하는 회원의 정보입니다.
     * @param deliveryAddressId - 삭제할 배송지의 번호 입니다.
     */
    void deleteDeliveryAddress(final MemberInfo memberInfo,
                               final Long deliveryAddressId);

    /**
     * 회원의 모든 배송지를 보여주는 GetMapping 메소드 입니다.
     *
     * @param memberInfo - 모든 배송지를 조회하는 회원의 정보입니다.
     * @return 조회하는 회원의 모든 배송정보를 담은 List 타입 입니다.
     */
    List<DeliveryAddressResponse> retrieveDeliveryAddresses(final MemberInfo memberInfo);
}
