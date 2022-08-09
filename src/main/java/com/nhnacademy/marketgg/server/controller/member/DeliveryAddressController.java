package com.nhnacademy.marketgg.server.controller.member;

import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.CreateDeliveryAddressRequest;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.UpdateDeliveryAddressRequest;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.service.deliveryaddress.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RoleCheck(accessLevel = Role.ROLE_USER)
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class DeliveryAddressController {
    private final DeliveryAddressService deliveryAddressService;

    @PostMapping("/delivery-address")
    public ResponseEntity<CommonResponse> createDeliveryAddress(MemberInfo memberInfo,
                                                                @Validated @RequestBody final CreateDeliveryAddressRequest deliveryAddressRequest) {
        deliveryAddressService.createDeliveryAddress(memberInfo, deliveryAddressRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Add success"));
    }

    @PatchMapping("/delivery-address")
    public ResponseEntity<CommonResponse> updateDeliveryAddress(MemberInfo memberInfo,
                                                                @Validated @RequestBody final UpdateDeliveryAddressRequest updateDeliveryAddressRequest) {
        deliveryAddressService.updateDeliveryAddress(memberInfo, updateDeliveryAddressRequest);
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

}
