package com.nhnacademy.marketgg.server.repository.cart;

import com.nhnacademy.marketgg.server.dto.response.CartResponse;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CartRepositoryCustom {

    List<CartResponse> findCartByMemberId(Long memberId);

}
