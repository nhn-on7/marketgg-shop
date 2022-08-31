package com.nhnacademy.marketgg.server.repository.productlabel;

import com.nhnacademy.marketgg.server.entity.ProductLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLabelRepository
        extends JpaRepository<ProductLabel, ProductLabel.Pk>, ProductLabelRepositoryCustom {

}
