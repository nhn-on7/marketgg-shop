package com.nhnacademy.marketgg.server.repository.asset;

import com.nhnacademy.marketgg.server.entity.Asset;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class AssetRepositoryImpl extends QuerydslRepositorySupport implements AssetRepositoryCustom {

    public AssetRepositoryImpl() {
        super(Asset.class);
    }

}
