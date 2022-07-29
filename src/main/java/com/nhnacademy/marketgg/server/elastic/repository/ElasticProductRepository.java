package com.nhnacademy.marketgg.server.elastic.repository;

import com.nhnacademy.marketgg.server.elastic.document.ElasticProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticProductRepository extends ElasticsearchRepository<ElasticProduct, Long> {

    void deleteAllByCategoryCode(final String categoryCode);

}
