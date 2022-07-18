package com.nhnacademy.marketgg.server.repository.productlabel;

import com.nhnacademy.marketgg.server.entity.ProductLabel;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ProductLabelRepositoryImpl extends QuerydslRepositorySupport implements ProductLabelRepositoryCustom {

    public ProductLabelRepositoryImpl() {
        super(ProductLabel.class);
    }

}
