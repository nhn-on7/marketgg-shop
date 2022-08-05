package com.nhnacademy.marketgg.server.elastic.repository;

import com.nhnacademy.marketgg.server.elastic.document.ElasticBoard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 엘라스틱서치의 게시판 동기화를 위한 Repository 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public interface ElasticBoardRepository extends ElasticsearchRepository<ElasticBoard, Long> {

    /**
     * 엘라스틱 서치 서버에 있는 해당 카테고리 번호를 지닌 게시글을 모두 삭제합니다.
     *
     * @param categoryCode - 삭제를 진행 할 게시판 타입의 식별번호입니다.
     * @since 1.0.0
     */
    void deleteAllByCategoryCode(final String categoryCode);

}
