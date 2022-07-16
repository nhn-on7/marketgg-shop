package com.nhnacademy.marketgg.server.repository.product;

import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ProductRepositoryCustom {

    /**
     * DB에 들어있는 모든 상품을 ProductResponse타입으로 반환합니다.
     *
     * @return - 상품 리스트를 반환합니다.
     * @since 1.0.0
     */
    List<ProductResponse> findAllProducts();

    /**
     * DB에서 PK값이 같은 상품을 찾아 반환 합니다.
     *
     * @param id - 상품의 PK 값 입니다.
     * @return 상품DTO를 반환합니다.
     * @since 1.0.0
     */
    ProductResponse queryById(final Long id);

    /**
     * DB에서 상품 이름 속성에 keyword가 포함된 모든 상품을 찾아 반환합니다.
     *
     * @param keyword - 검색을 위한 String 값입니다.
     * @return - 상품 목록을 반환합니다.
     * @since 1.0.0
     */
    List<ProductResponse> findByNameContaining(final String keyword);

    /**
     * DB에서 카테고리에 해당하는 모든 상품을 찾아 반환합니다.
     * ex) 100/101 -> 상품 - 채소에 해당하는 모든 상품을 반환
     *
     * @param categorizationCode - 카테고리 1차 분류입니다. ex) 100 - 상품
     * @param categoryCode - 카테고리 2차 분류입니다. ex) 101 - 채소
     * @return - 상품 목록을 반환합니다.
     */
    List<ProductResponse> findByCategoryAndCategorizationCodes(
            final String categorizationCode, final String categoryCode);

}
