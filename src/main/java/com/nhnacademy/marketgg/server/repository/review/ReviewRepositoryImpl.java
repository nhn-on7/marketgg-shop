package com.nhnacademy.marketgg.server.repository.review;

import static com.querydsl.core.types.ExpressionUtils.count;

import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.response.review.ReviewRatingResponse;
import com.nhnacademy.marketgg.server.dto.response.review.ReviewResponse;
import com.nhnacademy.marketgg.server.entity.QAsset;
import com.nhnacademy.marketgg.server.entity.QProduct;
import com.nhnacademy.marketgg.server.entity.QReview;
import com.nhnacademy.marketgg.server.entity.Review;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ReviewRepositoryImpl extends QuerydslRepositorySupport implements ReviewRepositoryCustom {

    public ReviewRepositoryImpl() {
        super(Review.class);
    }

    @Override
    public Page<ReviewResponse> retrieveReviews(final Pageable pageable, final Long productId) {
        QReview review = QReview.review;
        QAsset asset = QAsset.asset;
        QProduct product = QProduct.product;

        QueryResults<ReviewResponse> results = from(review)
            .select(Projections.constructor(ReviewResponse.class,
                                            review.id,
                                            review.member.id,
                                            review.asset.id,
                                            review.content,
                                            review.rating,
                                            review.isBest,
                                            review.createdAt,
                                            review.updatedAt,
                                            review.deletedAt,
                                            review.member.uuid))
            .innerJoin(asset).on(asset.id.eq(review.asset.id))
            .innerJoin(product).on(product.asset.id.eq(asset.id))
            .where(product.id.eq(productId))
            .offset(pageable.getOffset()).limit(pageable.getPageSize())
            .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public ReviewResponse queryById(final Long id) {
        QReview review = QReview.review;

        return from(review).select(selectAllReviewColumns()).where(review.id.eq(id)).fetchOne();
    }

    @Override
    public List<ReviewRatingResponse> retrieveReviewsByRating(final Long productId) {
        QReview review = QReview.review;
        QAsset asset = QAsset.asset;
        QProduct product = QProduct.product;

        return from(review).select(Projections.constructor(ReviewRatingResponse.class,
                                                           review.rating,
                                                           count(review.rating)))
                           .innerJoin(asset).on(asset.id.eq(review.asset.id))
                           .innerJoin(product).on(product.asset.id.eq(asset.id))
                           .where(product.id.eq(productId))
                           .groupBy(review.rating)
                           .orderBy(review.rating.asc())
                           .fetch();
    }

    @Override
    public Page<ReviewResponse> retrieveReviewsByMember(MemberInfo memberInfo, Pageable pageable) {
        QReview review = QReview.review;

        QueryResults<ReviewResponse> results = from(review)
            .select(Projections.constructor(ReviewResponse.class,
                                            review.id,
                                            review.member.id,
                                            review.asset.id,
                                            review.content,
                                            review.rating,
                                            review.isBest,
                                            review.createdAt,
                                            review.updatedAt,
                                            review.deletedAt,
                                            review.member.uuid))
            .where(review.member.id.eq(memberInfo.getId()))
            .orderBy(review.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private ConstructorExpression<ReviewResponse> selectAllReviewColumns() {
        QReview review = QReview.review;

        return Projections.constructor(ReviewResponse.class,
                                       review.id,
                                       review.member.id,
                                       review.asset.id,
                                       review.content,
                                       review.rating,
                                       review.isBest,
                                       review.createdAt,
                                       review.updatedAt,
                                       review.deletedAt,
                                       review.member.uuid);
    }
}
