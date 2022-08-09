package com.nhnacademy.marketgg.server.repository.category;

import com.nhnacademy.marketgg.server.dto.response.category.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.QCategorization;
import com.nhnacademy.marketgg.server.entity.QCategory;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {

    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    @Override
    public CategoryRetrieveResponse findByCode(String id) {
        QCategory category = QCategory.category;
        QCategorization categorization = QCategorization.categorization;

        return from(category)
            .innerJoin(categorization).on(category.categorization.id.eq(categorization.id))
            .where(category.id.eq(id))
            .select(selectAllCategoryColumns(category, categorization))
            .fetchOne();
    }

    @Override
    public String retrieveCategoryIdByName(String name) {
        QCategory category = QCategory.category;

        return from(category)
            .where(category.name.eq(name))
            .select(category.id)
            .fetchOne();
    }

    @Override
    public List<CategoryRetrieveResponse> findByCategorizationCode(String categorizationId) {
        QCategory category = QCategory.category;
        QCategorization categorization = QCategorization.categorization;

        return from(category)
            .innerJoin(categorization).on(category.categorization.id.eq(categorization.id))
            .where(categorization.id.eq(categorizationId))
            .select(selectAllCategoryColumns(category, categorization))
            .fetch();
    }

    @Override
    public List<CategoryRetrieveResponse> findAllCategories() {
        QCategory category = QCategory.category;
        QCategorization categorization = QCategorization.categorization;

        return from(category)
            .innerJoin(categorization).on(category.categorization.id.eq(categorization.id))
            .select(selectAllCategoryColumns(category, categorization))
            .fetch();
    }

    private ConstructorExpression<CategoryRetrieveResponse> selectAllCategoryColumns(QCategory category, QCategorization categorization) {
        return Projections.constructor(CategoryRetrieveResponse.class,
            category.id,
            categorization.name,
            category.name,
            category.sequence);
    }

}
