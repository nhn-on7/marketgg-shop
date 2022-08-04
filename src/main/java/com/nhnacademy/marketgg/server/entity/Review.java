package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.review.ReviewCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.review.ReviewUpdateRequest;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 후기 개체입니다.
 *
 * @since 1.0.0
 */
@Table(name = "reviews")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_no")
    @NotNull
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_no")
    @NotNull
    private Member member;

    @ManyToOne
    @JoinColumn(name = "asset_no")
    @NotNull
    private Asset asset;

    @Column
    @NotEmpty
    @Size(min = 10, max = 255)
    private String content;

    @Column
    private Long rating;

    @Column(name = "is_best")
    @NotNull
    private Boolean isBest;

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @NotNull
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Review(ReviewCreateRequest reviewRequest, Member member, Asset asset) {
        this.member = member;
        this.asset = asset;
        this.content = reviewRequest.getContent();
        this.rating = reviewRequest.getRating();
        this.isBest = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateReview(ReviewUpdateRequest reviewRequest, Asset asset) {
        this.content = reviewRequest.getContent();
        this.rating = reviewRequest.getRating();
        this.asset = asset;
    }

    public void makeBestReview() {
        this.isBest = true;
    }

}
