package com.nhnacademy.marketgg.server.elastic.repository;

import com.nhnacademy.marketgg.server.elastic.document.EsBoard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBoardRepository extends ElasticsearchRepository<EsBoard, Long> {

}
