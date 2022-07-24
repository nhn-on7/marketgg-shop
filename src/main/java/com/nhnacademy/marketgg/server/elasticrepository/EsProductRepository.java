package com.nhnacademy.marketgg.server.elasticrepository;

import com.nhnacademy.marketgg.server.entity.elastic.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * ES Server 의 Product 인덱스를 사용하게 될 Repository 입니다.
 * Jpa Repository 와 유사한 메소드를 사용하며 네이밍 메소드, @Query 또한 사용가능합니다.
 *
 * @version 1.0.0
 */
public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Long> {

}
