package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderRetrieveResponse;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface OrderRepositoryCustom {

    /**
     * 주문 목록을 조회하는 메소드입니다.
     *
     * @param memberId - 주문 목록을 조회하는 회원의 식별번호입니다.
     * @param isUser   - 조회하는 회원이 관리자인지, 일반회원인지의 여부입니다.
     * @return 일반 회원이라면 본인의 주문 목록, 관리자라면 전체 주문 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<OrderRetrieveResponse> findOrderList(final Long memberId, boolean isUser);

    /**
     * 주문 상세를 조회하는 메소드입니다.
     *
     * @param orderId  - 상세 조회할 주문의 식별번호입니다.
     * @param memberId - 주문 상세를 조회하는 회원의 식별번호입니다.
     * @param isUser   - 조회하는 회원이 관리자인지, 일반회원인지의 여부입니다.
     * @return 관리자라면 회원에 상관없이, 회원이라면 본인의 주문 상세를 반환합니다.
     * @since 1.0.0
     */
    OrderDetailRetrieveResponse findOrderDetail(final Long orderId, final Long memberId, final boolean isUser);

}
