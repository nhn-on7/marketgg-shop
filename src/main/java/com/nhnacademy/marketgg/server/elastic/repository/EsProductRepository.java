package com.nhnacademy.marketgg.server.elastic.repository;

import com.nhnacademy.marketgg.server.elastic.document.EsProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Long> {

}
