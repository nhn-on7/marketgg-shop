package com.nhnacademy.marketgg.server.repository.product;

import com.nhnacademy.marketgg.server.dto.response.product.ProductResponse;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.QProduct;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ProductRepositoryImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {

    public ProductRepositoryImpl() {
        super(Product.class);
    }

    @Override
    public Page<ProductResponse> findAllProducts(final Pageable pageable) {
        QProduct product = QProduct.product;

        QueryResults<ProductResponse> result = from(product)
            .select(selectAllProductColumns())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public ProductResponse queryById(final Long id) {
        QProduct product = QProduct.product;

        return from(product)
            .select(selectAllProductColumns())
            .where(product.id.eq(id))
            .fetchOne();
    }

    @Override
    public List<ProductResponse> findByNameContaining(final String keyword) {
        QProduct product = QProduct.product;

        return from(product)
            .select(selectAllProductColumns())
            .where(product.name.contains(keyword))
            .fetch();
    }

    @Override
    public List<ProductResponse> findByCategoryCode(final String categoryCode) {
        QProduct product = QProduct.product;

        return from(product)
            .select(selectAllProductColumns())
            .where(product.category.id.eq(categoryCode))
            .fetch();
    }

    private ConstructorExpression<ProductResponse> selectAllProductColumns() {
        QProduct product = QProduct.product;

        return Projections.constructor(ProductResponse.class,
            product.id,
            product.asset,
            product.asset.id,
            product.category,
            product.category.id,
            product.category.name,
            product.name,
            product.content,
            product.totalStock,
            product.price,
            product.description,
            product.unit,
            product.deliveryType,
            product.origin,
            product.packageType,
            product.expirationDate,
            product.allergyInfo,
            product.capacity,
            product.createdAt,
            product.updatedAt,
            product.deletedAt);
    }

}
