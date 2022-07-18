package com.nhnacademy.marketgg.server.repository.review;

import com.nhnacademy.marketgg.server.entity.Review;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ReviewRepositoryImpl extends QuerydslRepositorySupport implements ReviewRepositoryCustom {

    public ReviewRepositoryImpl() {
        super(Review.class);
    }

}
