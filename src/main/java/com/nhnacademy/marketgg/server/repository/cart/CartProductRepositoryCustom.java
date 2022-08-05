package com.nhnacademy.marketgg.server.repository.cart;

import com.nhnacademy.marketgg.server.dto.response.cart.CartProductResponse;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CartProductRepositoryCustom {

    List<CartProductResponse> findCartProductsByCartId(final Long cartId);

}
