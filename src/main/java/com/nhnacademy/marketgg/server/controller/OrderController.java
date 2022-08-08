package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.OrderCreateResponse;
import com.nhnacademy.marketgg.server.dto.response.OrderDetailResponse;
import com.nhnacademy.marketgg.server.dto.response.OrderResponse;
import com.nhnacademy.marketgg.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * 주문에 관련된 Rest Controller 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 */
// @RoleCheck(accessLevel = Role.ROLE_USER)
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private static final String ORDER_PREFIX = "/orders";

    // 주문서 작성
    @PostMapping
    public ResponseEntity<OrderCreateResponse> createOrder(@RequestBody final OrderCreateRequest orderRequest,
                                                           final MemberInfo memberInfo) {

        OrderCreateResponse response = orderService.createOrder(orderRequest, memberInfo.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(ORDER_PREFIX))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

    // 주문 목록 조회 - 관리자는 모든 회원, 회원은 본인 것
    @GetMapping
    public ResponseEntity<List<OrderResponse>> retrieveOrderList(final MemberInfo memberInfo) {
        List<OrderResponse> responses = orderService.retrieveOrderList(memberInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(responses);
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> retrieveOrderDetail(@PathVariable final Long orderId,
                                                                   final MemberInfo memberInfo) {

        OrderDetailResponse response = orderService.retrieveOrderDetail(orderId, memberInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

    // 운송장 번호 생성된 후 주문서 수정, 배송파트 담당자분이 처리한다고 하심
    @PatchMapping("/{orderId}")
    public ResponseEntity<Void> updateOrder() {

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    // 주문 취소 -> 주문서 삭제
    @PutMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable final Long orderId) {
        orderService.deleteOrder(orderId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
