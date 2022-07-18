package com.nhnacademy.marketgg.server.repository.crowdfunding;

import com.nhnacademy.marketgg.server.entity.Crowdfunding;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CrowdfundingRepositoryImpl extends QuerydslRepositorySupport implements CrowdfundingRepositoryCustom {

    public CrowdfundingRepositoryImpl() {
        super(Crowdfunding.class);
    }

}
