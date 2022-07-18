package com.nhnacademy.marketgg.server.repository.review;

import com.nhnacademy.marketgg.server.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

}
