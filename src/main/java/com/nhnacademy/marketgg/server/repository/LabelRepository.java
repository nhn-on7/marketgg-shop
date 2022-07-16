package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.response.LabelRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 라벨 레포지토리 입니다.
 *
 * @version 1.0.0
 */
public interface LabelRepository extends JpaRepository<Label, Long> {

    /**
     * 전체 라벨 목록을 반환하는 메소드입니다.
     *
     * @return 라벨 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @Query("SELECT l.id AS labelNo, " +
            "l.name AS name " +
            "FROM Label l")
    List<LabelRetrieveResponse> findAllLabels();
}
