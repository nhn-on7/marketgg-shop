package com.nhnacademy.marketgg.server.elasticrepository;

import com.nhnacademy.marketgg.server.entity.elastic.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Long> {

}
