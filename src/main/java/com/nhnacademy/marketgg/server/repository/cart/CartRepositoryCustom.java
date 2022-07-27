package com.nhnacademy.marketgg.server.repository.cart;

import com.nhnacademy.marketgg.server.dto.response.CartResponse;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * QueryDsl 을 위한 클래스입니다.
 * {@link com.nhnacademy.marketgg.server.repository.cart.CartRepositoryImpl}
 */
@NoRepositoryBean
public interface CartRepositoryCustom {

    /**
     * 사용자의 장바구니 목록을 조회합니다.
     *
     * @param memberId - 사용자 번호
     * @return - 장바구니 목록을 반환합니다.
     */
    List<CartResponse> findCartByMemberId(Long memberId);

}
