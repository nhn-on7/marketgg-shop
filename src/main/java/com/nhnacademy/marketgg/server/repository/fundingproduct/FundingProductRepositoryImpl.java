package com.nhnacademy.marketgg.server.repository.fundingproduct;

import com.nhnacademy.marketgg.server.entity.FundingProduct;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class FundingProductRepositoryImpl extends QuerydslRepositorySupport implements FundingProductRepositoryCustom {

    public FundingProductRepositoryImpl() {
        super(FundingProduct.class);
    }

}
