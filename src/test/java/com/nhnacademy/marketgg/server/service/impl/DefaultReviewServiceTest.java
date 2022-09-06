package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoRequest;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoResponse;
import com.nhnacademy.marketgg.server.dto.request.member.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.review.ReviewCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.review.ReviewUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.file.ImageResponse;
import com.nhnacademy.marketgg.server.dto.response.product.ProductDetailResponse;
import com.nhnacademy.marketgg.server.dto.response.review.ReviewResponse;
import com.nhnacademy.marketgg.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Review;
import com.nhnacademy.marketgg.server.eventlistener.event.savepoint.SavePointEvent;
import com.nhnacademy.marketgg.server.repository.asset.AssetRepository;
import com.nhnacademy.marketgg.server.repository.auth.AuthRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.repository.review.ReviewRepository;
import com.nhnacademy.marketgg.server.service.file.FileService;
import com.nhnacademy.marketgg.server.service.product.DefaultReviewService;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultReviewServiceTest {

    @InjectMocks
    DefaultReviewService reviewService;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    AssetRepository assetRepository;

    @Mock
    ApplicationEventPublisher publisher;

    @Mock
    FileService fileService;

    @Mock
    AuthRepository authRepository;

    @Mock
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    private Member member;
    private Review review;
    private Asset asset;
    private ReviewCreateRequest reviewRequest;
    private ReviewResponse reviewResponse;
    private ReviewUpdateRequest reviewUpdateRequest;
    private ImageResponse imageResponse;
    private MemberInfo memberInfo;
    private MemberInfoResponse memberInfoResponse;
    private ProductDetailResponse productDetailResponse;

    @BeforeEach
    void setUp() {
        MemberCreateRequest memberRequest = new MemberCreateRequest();
        member = new Member(memberRequest, new Cart());

        asset = Asset.create();

        reviewRequest = new ReviewCreateRequest();
        ReflectionTestUtils.setField(reviewRequest, "content", "리뷰내용");
        ReflectionTestUtils.setField(reviewRequest, "rating", 5L);
        review = new Review(reviewRequest, member, asset);

        reviewResponse = new ReviewResponse(1L,
                                            1L,
                                            1L,
                                            "content",
                                            5L,
                                            true,
                                            LocalDateTime.now(),
                                            LocalDateTime.now(),
                                            null,
                                            UUID.randomUUID().toString());

        reviewUpdateRequest = new ReviewUpdateRequest();
        ReflectionTestUtils.setField(reviewUpdateRequest, "reviewId", 1L);
        ReflectionTestUtils.setField(reviewUpdateRequest, "assetId", 1L);
        ReflectionTestUtils.setField(reviewUpdateRequest, "content", "리뷰 수정합니다~");
        ReflectionTestUtils.setField(reviewUpdateRequest, "rating", 5L);

        imageResponse = new ImageResponse("이미지 응답", 1L, "이미지 주소",  asset);

        memberInfo = Dummy.getDummyMemberInfo(1L, Dummy.getDummyCart(1L));
        memberInfoResponse = new MemberInfoResponse("admin", "ssasdfsdaf@gmail.com", "010-1234-1234");
        productDetailResponse = Dummy.getDummyProductResponse();
    }

    @Test
    @DisplayName("이미지 리뷰 생성 성공 테스트")
    void testCreateReview() throws IOException {

        URL url = getClass().getClassLoader().getResource("img/lee.png");
        String filePath = Objects.requireNonNull(url).getPath();

        MockMultipartFile image =
                new MockMultipartFile("images", "img/lee.png", "image/png", new FileInputStream(filePath));

        given(memberRepository.findById(anyLong())).willReturn(Optional.ofNullable(member));
        given(reviewRepository.save(any(Review.class))).willReturn(review);
        given(fileService.uploadImage(any(MultipartFile.class))).willReturn(imageResponse);
        willDoNothing().given(publisher).publishEvent(any(SavePointEvent.class));

        this.reviewService.createReview(reviewRequest, image, memberInfo);

        then(reviewRepository).should(times(1)).save(any(Review.class));
        then(publisher).should(times(1)).publishEvent(any(SavePointEvent.class));
    }

    @Test
    @DisplayName("일반 리뷰 생성 성공 테스트")
    void testTextReview() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.ofNullable(member));
        given(reviewRepository.save(any(Review.class))).willReturn(review);
        given(productRepository.queryById(anyLong())).willReturn(productDetailResponse);
        willDoNothing().given(publisher).publishEvent(any(SavePointEvent.class));

        this.reviewService.createReview(reviewRequest, memberInfo, 1L);

        then(reviewRepository).should(times(1)).save(any(Review.class));
        then(publisher).should(times(1)).publishEvent(any(SavePointEvent.class));
    }

    @Test
    @DisplayName("후기 전체 조회 테스트")
    void testRetrieveReviews() throws JsonProcessingException {
        List<ReviewResponse> list = List.of(reviewResponse);
        Page<ReviewResponse> page = new PageImpl<>(list, PageRequest.of(0, 1), 1);
        given(reviewRepository.retrieveReviews(PageRequest.of(0, 1), 1L)).willReturn(page);
        given(authRepository.getMemberInfo(any(MemberInfoRequest.class))).willReturn(ShopResult.successWith(memberInfoResponse));

        reviewService.retrieveReviews(page.getPageable(), 1L);

        then(reviewRepository).should(times(1)).retrieveReviews(page.getPageable(), 1L);
    }

    @Test
    @DisplayName("후기 상세 조회 테스트")
    void testRetrieveReviewDetails() {
        given(reviewRepository.queryById(anyLong())).willReturn(reviewResponse);

        reviewService.retrieveReviewDetails(1L);

        then(reviewRepository).should(times(1)).queryById(anyLong());
    }

    @Test
    @DisplayName("후기 수정 테스트")
    void testUpdateReview() {
        given(assetRepository.findById(anyLong())).willReturn(Optional.ofNullable(asset));
        given(reviewRepository.findById(anyLong())).willReturn(Optional.of(review));

        this.reviewService.updateReview(reviewUpdateRequest, 1L);

        then(reviewRepository).should(times(1)).findById(anyLong());
        then(reviewRepository).should(times(1)).save(any(Review.class));
    }

    @Test
    @DisplayName("후기 삭제 테스트")
    void testDeleteReview() {
        given(reviewRepository.findById(anyLong())).willReturn(Optional.ofNullable(review));

        this.reviewService.deleteReview(1L);

        then(reviewRepository).should(times(1)).delete(any(Review.class));
    }

    @Test
    @DisplayName("베스트후기 선정 테스트")
    void testMakeBestReview() {
        given(reviewRepository.findById(anyLong())).willReturn(Optional.ofNullable(review));

        Boolean response = this.reviewService.makeBestReview(1L);

        assertThat(response).isTrue();
        then(reviewRepository).should(times(1)).findById(anyLong());
        then(reviewRepository).should(times(1)).save(any(Review.class));
    }

}
