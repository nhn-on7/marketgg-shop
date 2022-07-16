package com.nhnacademy.marketgg.server.repository.product;

import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.QProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ProductRepositoryImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {

    public ProductRepositoryImpl() {
        super(Product.class);
    }

    @Override
    public List<ProductResponse> findAllProducts() {
        QProduct product = QProduct.product;

        return from(product)
                .select(selectAllProductColumns())
                .fetch();
    }

    @Override
    public ProductResponse queryById(Long id) {
        QProduct product = QProduct.product;

        return from(product)
                .select(selectAllProductColumns())
                .where(product.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<ProductResponse> findByNameContaining(String keyword) {
        QProduct product = QProduct.product;

        return from(product)
                .select(selectAllProductColumns())
                .where(product.name.contains(keyword))
                .fetch();
    }

    @Override
    public List<ProductResponse> findByCategoryAndCategorizationCodes(String categorizationCode, String categoryCode) {
        QProduct product = QProduct.product;

        return from(product)
                .select(selectAllProductColumns())
                .where(product.category.categorization.id.eq(categorizationCode))
                .where(product.category.id.eq(categoryCode))
                .fetch();
    }

    private QBean<ProductResponse> selectAllProductColumns() {
        QProduct product = QProduct.product;

        return Projections.bean(ProductResponse.class,
                                product.id,
                                product.asset,
                                product.category,
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
