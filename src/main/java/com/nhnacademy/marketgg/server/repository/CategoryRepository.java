package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 카테고리 레포지토리 입니다.
 *
 * @version 1.0.0
 */
public interface CategoryRepository extends JpaRepository<Category, String> {

    /**
     * 지정한 카테고리를 반환하는 메소드입니다.
     *
     * @param id - 반환할 카테고리의 식별번호입니다.
     * @return - 지정한 카테고리의 정보를 담은 객체를 반환합니다.
     * @since 1.0.0
     */
    @Query("SELECT c.id as categoryCode, " +
            "cz.name as categorizationName, " +
            "c.name as categoryName, " +
            "c.sequence as sequence " +
            "FROM Category c " +
            "   INNER JOIN Categorization cz " +
            "   ON cz.id = c.categorization.id " +
            "WHERE c.id = :id")
    CategoryRetrieveResponse findByCode(final String id);

    /**
     * 전체 카테고리 목록을 반환하는 메소드입니다.
     *
     * @return 카테고리 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @Query("SELECT c.id as categoryCode, " +
            "cz.name as categorizationName, " +
            "c.name as categoryName, " +
            "c.sequence as sequence " +
            "FROM Category c " +
            "   INNER JOIN Categorization cz " +
            "   ON c.categorization.id = cz.id")
    List<CategoryRetrieveResponse> findAllCategories();

}
