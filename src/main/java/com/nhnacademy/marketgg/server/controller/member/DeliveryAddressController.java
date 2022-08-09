package com.nhnacademy.marketgg.server.controller.member;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RoleCheck(accessLevel = Role.ROLE_USER)
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class DeliveryAddressController {
    private final DeliveryAddressService deliveryAddressService;

    @PostMapping("/delivery-address")
    public ResponseEntity<CommonResponse> createDeliveryAddress(MemberInfo member,
                                                                @Validated @RequestBody final DeliveryAddressCreateRequest deliveryAddressRequest) {
        deliveryAddressService.createDeliveryAddress(member, deliveryAddressRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Add success"));
    }

    @PatchMapping("/delivery-address")
    public ResponseEntity<CommonResponse> updateDeliveryAddress(MemberInfo member,
                                                                @Validated @RequestBody final DeliveryAddressUpdateRequest deliveryAddressUpdateRequest) {
        deliveryAddressService.updateDeliveryAddress(member, deliveryAddressUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Update success"));
    }

    @DeleteMapping("/delivery-address")
    public ResponseEntity<CommonResponse> deleteDeliveryAddress(MemberInfo memberInfo) {
        deliveryAddressService.deleteDeliveryAddress(memberInfo);
        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Delete success"));
    }

    @GetMapping("/delivery-addresses")
    public ResponseEntity<CommonResponse> retrieveDeliveryAddresses(MemberInfo member) {
        List<DeliveryAddressResponse> deliveryAddresses = deliveryAddressService.retrieveDeliveryAddresses(member);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ListResponse<>(deliveryAddresses));
    }

}
