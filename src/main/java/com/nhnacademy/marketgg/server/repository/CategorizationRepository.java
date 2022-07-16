package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Categorization;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 카테고리 분류 레포지토리 입니다.
 *
 * @version 1.0.0
 */
public interface CategorizationRepository extends JpaRepository<Categorization, String> {

    /**
     * 전체 카테고리 분류 목록을 반환하는 메소드입니다.
     *
     * @return 카테고리 분류 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @Query("SELECT cz.id AS categorizationCode, " +
            "cz.name AS name " +
            "FROM Categorization cz")
    List<CategorizationRetrieveResponse> findAllCategorization();

}
