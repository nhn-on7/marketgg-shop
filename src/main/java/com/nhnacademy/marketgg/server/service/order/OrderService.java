package com.nhnacademy.marketgg.server.service.order;

import com.nhnacademy.marketgg.server.dto.info.AuthInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.order.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderFormResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;

import java.util.List;

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
     * @param memberId     - 주문을 등록하는 회원의 식별번호입니다.
     * @return - 주문 등록 후 결제에 넘겨줄 정보를 담은 DTO 를 반환합니다.
     * @since 1.0.0
     */
    OrderToPayment createOrder(final OrderCreateRequest orderRequest, final Long memberId);

    // memo: javadoc
    OrderFormResponse retrieveOrderForm(final List<ProductToOrder> products, final MemberInfo memberInfo,
                                        final AuthInfo authInfo);

    /**
     * 주문 목록을 조회하는 메소드입니다.
     *
     * @param memberInfo - 주문 목록을 조회하는 회원의 정보입니다.
     * @return - 조회하는 회원의 종류에 따라 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<OrderRetrieveResponse> retrieveOrderList(final MemberInfo memberInfo);

    /**
     * 주문 상세를 조회하는 메소드입니다.
     *
     * @param orderId    - 조회할 주문의 식별번호입니다.
     * @param memberInfo - 주문 상세를 조회할 회원의 정보입니다.
     * @return 조회하는 회원의 종류에 따라 상세 조회 정보를 반환합니다.
     * @since 1.0.0
     */
    OrderDetailRetrieveResponse retrieveOrderDetail(final Long orderId, final MemberInfo memberInfo);

    /**
     * 주문(내역)을 삭제하는 메소드입니다.
     *
     * @param orderId - 삭제할 주문의 식별번호입니다.
     * @since 1.0.0
     */
    void deleteOrder(final Long orderId);
}
