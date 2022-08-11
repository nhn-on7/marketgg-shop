package com.nhnacademy.marketgg.server.repository.product;

import com.nhnacademy.marketgg.server.dto.response.product.ProductResponse;
import java.util.List;

import com.nhnacademy.marketgg.server.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ProductRepositoryCustom {

    /**
     * DB에 들어있는 모든 상품을 ProductResponse타입으로 반환합니다.
     *
     * @return - 상품 리스트를 반환합니다.
     * @since 1.0.0
     */
    Page<ProductResponse> findAllProducts(Pageable pageable);

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
     * @param categoryCode - 카테고리 2차 분류입니다. ex) 101 - 채소
     * @return - 상품 목록을 반환합니다.
     */
    List<ProductResponse> findByCategoryCode(final String categoryCode);

    /**
     * 상품의 식별번호 목록으로 상품 목록을 조회합니다.
     *
     * @param productIds - 조회할 상품 식별번호 목록입니다.
     * @return 상품 식별번호 목록으로 조회한 상품 목록을 반환합니다.
     * @since 1.0.0
     * @author 김정민
     */
    List<Product> findByIds(final List<Long> productIds);

}
