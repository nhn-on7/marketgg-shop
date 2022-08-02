package com.nhnacademy.marketgg.server.elastic.repository;

import com.nhnacademy.marketgg.server.elastic.document.ElasticProduct;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticProductRepository extends ElasticsearchRepository<ElasticProduct, Long> {

    void deleteAllByCategoryCode(final String categoryCode);

    List<ElasticProduct> findAllByLabelName(final String name);

    Page<ElasticProduct> findAllByCategoryCode(final Pageable pageable, final String categoryCode);

}
