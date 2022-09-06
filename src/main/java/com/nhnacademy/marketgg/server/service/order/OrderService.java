package com.nhnacademy.marketgg.server.service.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.info.AuthInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.order.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.order.OrderUpdateStatusRequest;
import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderFormResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderPaymentKey;
import com.nhnacademy.marketgg.server.dto.response.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 주문 Service 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 */
public interface OrderService {

    /**
     * 주문을 등록하는 메소드입니다.
     *
     * @param orderRequest - 주문을 등록하기 위한 정보를 담은 DTO 입니다.
     * @param memberInfo   - 주문을 등록하는 회원의 정보입니다.
     * @return 주문 등록 후 결제에 넘겨줄 정보를 담은 DTO 를 반환합니다.
     * @since 1.0.0
     */
    OrderToPayment createOrder(final OrderCreateRequest orderRequest, final MemberInfo memberInfo)
            throws JsonProcessingException;

    /**
     * 주문서 작성에 필요한 정보를 취합하여 조회하는 메소드입니다.
     *
     * @param productIds - 주문할 상품 ID 목록입니다.
     * @param memberInfo - 주문하는 회원의 정보입니다.
     * @param authInfo   - 주문하는 회원의 auth 정보입니다.
     * @return 취합한 정보를 반환합니다.
     * @since 1.0.0
     */
    OrderFormResponse retrieveOrderForm(final List<Long> productIds, final MemberInfo memberInfo,
                                        final AuthInfo authInfo);

    /**
     * 주문 목록을 조회하는 메소드입니다.
     *
     * @param memberInfo - 주문 목록을 조회하는 회원의 정보입니다.
     * @return 조회하는 회원의 종류에 따라 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    Page<OrderRetrieveResponse> retrieveOrderList(final MemberInfo memberInfo, final Pageable pageable);

    /**
     * 주문 상세를 조회하는 메소드입니다.
     *
     * @param orderId    - 조회할 주문의 식별번호입니다.
     * @param memberInfo - 주문 상세를 조회할 회원의 정보입니다.
     * @return 조회하는 회원의 종류에 따라 상세 조회 정보를 반환합니다.
     * @since 1.0.0
     */
    OrderDetailRetrieveResponse retrieveOrderDetail(final Long orderId, final MemberInfo memberInfo) throws JsonProcessingException;

    /**
     * 주문 상태를 변경하는 메소드입니다.
     *
     * @param orderId - 변경할 주문의 식별번호입니다.
     * @param status  - 변경할 상태값입니다.
     * @since 1.0.0
     */
    void updateStatus(final Long orderId, final OrderUpdateStatusRequest status);

    /**
     * 주문의 운송장 번호를 발급받기 위한 메소드입니다.
     *
     * @param orderId - 운송장 번호를 발급받을 주문의 식별번호입니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    void createTrackingNo(final Long orderId) throws JsonProcessingException;

    /**
     * 주문(내역)을 취소하는 메소드입니다.
     *
     * @param orderId - 취소할 주문의 식별번호입니다.
     * @since 1.0.0
     */
    void cancelOrder(final Long orderId);

    /**
     * 주문의 paymentKey 를 조회하는 메소드입니다.
     *
     * @param orderId - 조회할 주문의 식별번호입니다.
     * @param memberInfo - 조회하는 회원의 정보입니다.
     * @return 조회한 payment 를 반환합니다.
     */
    OrderPaymentKey retrieveOrderPaymentKey(final Long orderId, final MemberInfo memberInfo);

    /**
     * 주문 번호에 prefix 를 붙이는 메소드입니다.
     *
     * @param orderId - 주문의 식별번호입니다.
     * @return 주문 식별번호에 prefix 를 붙인 주문번호를 반환합니다.
     * @since 1.0.0
     */
    default String attachPrefix(final Long orderId) {
        return "GGORDER_" + orderId;
    }

    /**
     * 주문 번호에 prefix 를 떼는 메소드입니다.
     *
     * @param orderId - 주문의 식별번호입니다.
     * @return 주문 번호에서 prefix 를 뗀 주문 식별번호를 반환합니다.
     * @since 1.0.0
     */
    default Long detachPrefix(final String orderId) {
        return Long.valueOf(orderId.substring(orderId.indexOf("_") + 1));
    }

}
