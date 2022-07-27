package com.nhnacademy.marketgg.server.elastic.repository;

import com.nhnacademy.marketgg.server.elastic.document.ElasticBoard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticBoardRepository extends ElasticsearchRepository<ElasticBoard, Long> {

}
