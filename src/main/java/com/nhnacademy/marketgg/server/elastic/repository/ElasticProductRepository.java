package com.nhnacademy.marketgg.server.elastic.repository;

import com.nhnacademy.marketgg.server.elastic.document.ElasticProduct;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 엘라스틱 서치의 상품의 동기화를 위한 Repository 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public interface ElasticProductRepository extends ElasticsearchRepository<ElasticProduct, Long> {

    /**
     * 지정한 카테고리 식별번호를 지닌 상품들을 모두 삭제합니다.
     *
     * @param categoryCode - 지정한 카테고리의 식별번호입니다.
     * @since 1.0.0
     */
    void deleteAllByCategoryCode(final String categoryCode);

    /**
     * 입력받은 라벨 이름으로 모든 상품을 찾습니다.
     *
     * @param name - 입력한 라벨의 이름입니다.
     * @return 해당 라벨을 지닌 상품들을 모두 반환합니다.
     * @since 1.0.0
     */
    List<ElasticProduct> findAllByLabelName(final String name);

    /**
     * 지정한 카테고리를 지닌 상품 목록을 모두 조회합니다.
     *
     * @param pageable     - 조회할 페이지의 정보입니다.
     * @param categoryCode - 조회를 진행 할 카테고리 식별번호입니다.
     * @return 지정한 카테고리를 지닌 상품 목록을 반환합니다.
     * @since 1.0.0
     */
    Page<ElasticProduct> findAllByCategoryCode(final Pageable pageable, final String categoryCode);

}
