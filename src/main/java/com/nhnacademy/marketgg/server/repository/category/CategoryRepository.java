package com.nhnacademy.marketgg.server.repository.category;

import com.nhnacademy.marketgg.server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 카테고리 레포지토리 입니다.
 *
 * @author 박세완, 김정민
 * @version 1.0.0
 */
public interface CategoryRepository extends JpaRepository<Category, String>, CategoryRepositoryCustom {

}
