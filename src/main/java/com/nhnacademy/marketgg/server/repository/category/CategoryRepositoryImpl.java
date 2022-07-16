package com.nhnacademy.marketgg.server.repository.category;

import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.QCategorization;
import com.nhnacademy.marketgg.server.entity.QCategory;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {

    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    @Override
    public CategoryRetrieveResponse findByCode(String id) {
        QCategory category = QCategory.category;
        QCategorization categorization = QCategorization.categorization;

        return from(category)
                .innerJoin(category.categorization).on(category.categorization.id.eq(categorization.id))
                .where(category.id.eq(id))
                .select(Projections.bean(CategoryRetrieveResponse.class,
                                         category.id,
                                         categorization.name,
                                         category.name,
                                         category.sequence))
                .fetchOne();
    }

    @Override
    public List<CategoryRetrieveResponse> findAllCategories() {
        QCategory category = QCategory.category;
        QCategorization categorization = QCategorization.categorization;

        return from(category)
                .innerJoin(category.categorization).on(category.categorization.id.eq(categorization.id))
                .select(Projections.bean(CategoryRetrieveResponse.class,
                                         category.id,
                                         categorization.name,
                                         category.name,
                                         category.sequence))
                .fetch();
    }

}
