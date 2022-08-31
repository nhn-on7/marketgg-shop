package com.nhnacademy.marketgg.server.repository.categorization;

import com.nhnacademy.marketgg.server.entity.Categorization;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 카테고리 분류 레포지토리 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public interface CategorizationRepository
        extends JpaRepository<Categorization, String>, CategorizationRepositoryCustom {

}
