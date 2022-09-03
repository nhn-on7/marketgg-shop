package com.nhnacademy.marketgg.server.dto.response.review;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReviewRatingResponse {

    private final Long rating;

    private final Long ratingCount;
}
