package com.nhnacademy.marketgg.server.controller.order;

import com.nhnacademy.marketgg.server.dto.info.AuthInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.order.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderFormResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;
import com.nhnacademy.marketgg.server.service.order.OrderService;
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

    // memo: 주문서 등록 , 결제요청 url 로 location 주기
    @PostMapping
    public ResponseEntity<OrderToPayment> createOrder(@RequestBody final OrderCreateRequest orderRequest,
                                                      final MemberInfo memberInfo) {

        OrderToPayment response = orderService.createOrder(orderRequest, memberInfo.getId());
        String orderId = response.getOrderId().substring(response.getOrderId().indexOf("_")+1);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(ORDER_PREFIX + "/" + orderId + "/request-payment"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

    // memo: 주문서 작성폼 정보 조회
    @GetMapping("/order-form")
    public ResponseEntity<OrderFormResponse> retrieveOrderForm(@RequestBody final List<ProductToOrder> products,
                                                               final MemberInfo memberInfo, final AuthInfo authInfo) {

        OrderFormResponse response = orderService.retrieveOrderForm(products, memberInfo, authInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX + "/orderForm"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

    // memo: 주문 목록 조회 - 관리자는 모든 회원, 회원은 본인 것
    @GetMapping
    public ResponseEntity<List<OrderRetrieveResponse>> retrieveOrderList(final MemberInfo memberInfo) {
        List<OrderRetrieveResponse> responses = orderService.retrieveOrderList(memberInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(responses);
    }

    // memo: 주문 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailRetrieveResponse> retrieveOrderDetail(@PathVariable final Long orderId,
                                                                           final MemberInfo memberInfo) {

        OrderDetailRetrieveResponse response = orderService.retrieveOrderDetail(orderId, memberInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX + "/" + orderId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

    // memo: 주문 상태 변경
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable final Long orderId) {

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX + "/" + orderId + "/status"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    // memo: 운송장 번호 생성된 후 주문서 수정, 배송파트 담당자분이 처리한다고 하심
    @PatchMapping("/{orderId}/delivery")
    public ResponseEntity<Void> createDeliveryNumber(@PathVariable final Long orderId) {

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX + "/" + orderId + "/delivery"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    // memo: 주문 취소 -> 주문서 삭제
    @PutMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable final Long orderId) {
        orderService.deleteOrder(orderId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX + "/" + orderId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
