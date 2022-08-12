package com.nhnacademy.marketgg.server.controller.member;

import com.nhnacademy.marketgg.server.annotation.Auth;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.ListResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import com.nhnacademy.marketgg.server.service.deliveryaddress.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 배송지 관리 Rest Controller 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
@Auth
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class DeliveryAddressController {
    private final DeliveryAddressService deliveryAddressService;

    /**
     * 회원이 회원가입 외 추가적으로 배송지를 등록하는 PostMapping 메소드 입니다.
     *
     * @param memberInfo    - 배송지를 등록하는 회원의 정보입니다.
     * @param createRequest - 추가할 배송지를 담은 DTO 객체 입니다.
     * @return CREATED 상태 코드를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/delivery-address")
    public ResponseEntity<CommonResponse> createDeliveryAddress(final MemberInfo memberInfo,
                                                                @Validated @RequestBody final DeliveryAddressCreateRequest createRequest) {

        deliveryAddressService.createDeliveryAddress(memberInfo, createRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Add success"));
    }

    /**
     * 회원이 배송지를 수정할 때 사용하는 PatchMapping 메소드 입니다.
     *
     * @param memberInfo    - 배송지를 수정하는 회원의 정보입니다.
     * @param updateRequest - 수정할 배송지를 담은 DTO 객체 입니다.
     * @return OK 상태 코드를 반환합니다.
     * @since 1.0.0
     */
    @PutMapping("/delivery-address")
    public ResponseEntity<CommonResponse> updateDeliveryAddress(final MemberInfo memberInfo,
                                                                @Validated @RequestBody final DeliveryAddressUpdateRequest updateRequest) {

        deliveryAddressService.updateDeliveryAddress(memberInfo, updateRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Update success"));
    }

    /**
     * 회원이 배송지를 삭제할 때 사용하는 DeleteMapping 메소드 입니다.
     *
     * @param memberInfo        - 배송지를 삭제하는 회원의 정보입니다.
     * @param deliveryAddressNo - 삭제할 배송지의 번호 입니다.
     * @return OK 상태 코드를 반환합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/delivery-address/{deliveryAddressNo}")
    public ResponseEntity<CommonResponse> deleteDeliveryAddress(final MemberInfo memberInfo,
                                                                @PathVariable final Long deliveryAddressNo) {

        deliveryAddressService.deleteDeliveryAddress(memberInfo, deliveryAddressNo);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Delete success"));
    }

    /**
     * 회원의 모든 배송지를 보여주는 GetMapping 메소드 입니다.
     *
     * @param memberInfo - 모든 배송지를 조회하는 회원의 정보입니다.
     * @return OK 상태코드와 모든 배송정보를 담은 List 타입 입니다.
     */
    @GetMapping("/delivery-addresses")
    public ResponseEntity<CommonResponse> retrieveDeliveryAddresses(final MemberInfo memberInfo) {

        List<DeliveryAddressResponse> deliveryAddresses = deliveryAddressService.retrieveDeliveryAddresses(memberInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ListResponse<>(deliveryAddresses));
    }

}
