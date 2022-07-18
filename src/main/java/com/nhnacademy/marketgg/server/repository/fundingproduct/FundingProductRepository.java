package com.nhnacademy.marketgg.server.repository.fundingproduct;

import com.nhnacademy.marketgg.server.entity.FundingProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingProductRepository extends JpaRepository<FundingProduct, Long>, FundingProductRepositoryCustom {

}
