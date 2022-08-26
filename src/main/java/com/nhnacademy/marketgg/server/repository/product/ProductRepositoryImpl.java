package com.nhnacademy.marketgg.server.repository.product;

import com.nhnacademy.marketgg.server.dto.response.product.ProductDetailResponse;
import com.nhnacademy.marketgg.server.dto.response.product.ProductListResponse;
import com.nhnacademy.marketgg.server.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {

    public ProductRepositoryImpl() {
        super(Product.class);
    }

    @Override
    public Page<ProductListResponse> findAllProducts(final Pageable pageable) {
        QProduct product = QProduct.product;
        QProductLabel productLabel = QProductLabel.productLabel;
        QLabel label = QLabel.label;
        QImage image = QImage.image;

        QueryResults<ProductListResponse> result = from(product)
                .select(Projections.constructor(ProductListResponse.class,
                        product.id,
                        product.category.id.as("categoryCode"),
                        product.name.as("productName"),
                        product.content,
                        product.description,
                        label.name.as("labelName"),
                        image.imageAddress,
                        product.price,
                        product.totalStock.as("amount")
                )).innerJoin(productLabel).on(productLabel.product.id.eq(product.id))
                .innerJoin(label).on(label.id.eq(productLabel.label.id))
                .innerJoin(image).on(image.asset.id.eq(product.asset.id))
                .where(product.deletedAt.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public ProductDetailResponse queryById(final Long id) {
        QProduct product = QProduct.product;

        return from(product)
                .select(selectAllProductColumns())
                .where(product.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<ProductDetailResponse> findByNameContaining(final String keyword) {
        QProduct product = QProduct.product;

        return from(product)
                .select(selectAllProductColumns())
                .where(product.name.contains(keyword))
                .fetch();
    }

    @Override
    public List<ProductDetailResponse> findByCategoryCode(final String categoryCode) {
        QProduct product = QProduct.product;

        return from(product)
                .select(selectAllProductColumns())
                .where(product.category.id.eq(categoryCode))
                .fetch();
    }

    @Override
    public List<Product> findByIds(List<Long> productIds) {
        QProduct product = QProduct.product;
        List<Product> result = new ArrayList<>();

        for (Long id : productIds) {
            Product prod = from(product)
                    .select(product)
                    .where(product.id.eq(id))
                    .fetchOne();

            result.add(prod);
        }

        return result;
    }

    private ConstructorExpression<ProductDetailResponse> selectAllProductColumns() {
        QProduct product = QProduct.product;

        return Projections.constructor(ProductDetailResponse.class,
                product.id,
                product.asset,
                product.asset.id,
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
