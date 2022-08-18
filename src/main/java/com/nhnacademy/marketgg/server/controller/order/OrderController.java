package com.nhnacademy.marketgg.server.controller.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.delivery.DeliveryRepository;
import com.nhnacademy.marketgg.server.dto.info.AuthInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.order.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.order.OrderUpdateStatusRequest;
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
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private static final String ORDER_PREFIX = "/orders";

    /**
     * 주문을 등록하는 POST Mapping 을 지원합니다.
     *
     * @param orderRequest - 주문을 등록하기 위한 정보입니다.
     * @param memberInfo - 주문하는 회원의 정보입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping
    public ResponseEntity<OrderToPayment> createOrder(@RequestBody final OrderCreateRequest orderRequest,
                                                      final MemberInfo memberInfo) {

        OrderToPayment response = orderService.createOrder(orderRequest, memberInfo.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/payments/verify"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

    /**
     * 주문서 작성에 필요한 정보를 취합하여 제공하는 GET Mapping 을 지원합니다.
     *
     * @param products - 주문할 상품 목록입니다.
     * @param memberInfo - 주문하는 회원의 정보입니다.
     * @param authInfo - 주문하는 회원의 auth 정보입니다.
     * @return 회원의 정보를 토대로 주문서 작성에 필요한 값들과 상품목록을 취합하여 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/order-form")
    public ResponseEntity<OrderFormResponse> retrieveOrderForm(@RequestBody final List<ProductToOrder> products,
                                                               final MemberInfo memberInfo, final AuthInfo authInfo) {

        OrderFormResponse response = orderService.retrieveOrderForm(products, memberInfo, authInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX + "/orderForm"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

    /**
     * 주문 내역에서 볼 수 있는 주문 목록을 조회하는 GET Mapping 을 지원합니다.
     * 관리자는 모든 회원에 대한 주문, 삭제된 주문 내역도 볼 수 있습니다.
     *
     * @param memberInfo - 조회하는 회원의 정보입니다.
     * @return 주문 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<List<OrderRetrieveResponse>> retrieveOrderList(final MemberInfo memberInfo) {
        List<OrderRetrieveResponse> responses = orderService.retrieveOrderList(memberInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(responses);
    }

    /**
     * 주문 상세 조회를 할 수 있는 GET Mapping 을 지원합니다.
     *
     * @param orderId - 상세 조회할 주문의 식별번호입니다.
     * @param memberInfo - 조회하는 회원의 정보입니다.
     * @return 상세 조회한 주문을 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailRetrieveResponse> retrieveOrderDetail(@PathVariable final Long orderId,
                                                                           final MemberInfo memberInfo) {

        OrderDetailRetrieveResponse response = orderService.retrieveOrderDetail(orderId, memberInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX + "/" + orderId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

    /**
     * 주문의 상태를 변경하는 PATCH Mapping 을 지원합니다.
     *
     * @param orderId - 상태를 변경할 주문의 식별번호입니다.
     * @param status - 변경할 상태의 값입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable final Long orderId,
                                             @RequestBody final OrderUpdateStatusRequest status) {

        orderService.updateStatus(orderId, status);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX + "/" + orderId + "/status"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 주문에 운송장 번호 발급을 요청하는 PATCH Mapping 을 지원합니다.
     *
     * @param orderId - 운송장 번호를 발급하려는 주문의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @PatchMapping("/{orderId}/delivery")
    public ResponseEntity<Void> createTrackingNo(@PathVariable final Long orderId) throws JsonProcessingException {
        orderService.createTrackingNo(orderId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ORDER_PREFIX + "/" + orderId + "/delivery"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 주문을 취소할 수 있는 PUT Mapping 을 지원합니다.(소프트 삭제)
     *
     * @param orderId - 취소할 주문의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable final Long orderId) {
        // memo: 주문 취소 -> 결제 취소 요청 -> 사용쿠폰 삭제 -> 포인트 차감 및 적립 내역 추가
        orderService.deleteOrder(orderId);

        return ResponseEntity.status(HttpStatus.OK)
                             // memo: 결제 취소 요청할 페이지 미정
                             .location(URI.create(ORDER_PREFIX + "/" + orderId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
