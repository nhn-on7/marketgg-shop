package com.nhnacademy.marketgg.server.repository.productinquirypost;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.nhnacademy.marketgg.server.dto.request.member.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberGradeCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.product.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import com.nhnacademy.marketgg.server.repository.asset.AssetRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;


@DataJpaTest
class ProductInquiryPostRepositoryImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductInquiryPostRepository productInquiryPostRepository;

    @Autowired
    AssetRepository assetRepository;

    ProductInquiryPost productInquiryPost;
    Member member;
    Product product;
    ProductInquiryRequest productInquiryRequest;
    Asset asset;

    @BeforeEach
    void setUp() {
        productInquiryRequest = new ProductInquiryRequest();
        ReflectionTestUtils.setField(productInquiryRequest, "title", "문의 제목");
        ReflectionTestUtils.setField(productInquiryRequest, "content", "문의 내용");
        ReflectionTestUtils.setField(productInquiryRequest, "isSecret", true);

        member = Dummy.getDummyMember(Dummy.getDummyCart(1L));
        memberRepository.save(member);

        asset = Asset.create();
        assetRepository.save(asset);

        product = Dummy.getDummyProduct(1L, 1L);
        productRepository.save(product);

        productInquiryPost = new ProductInquiryPost(1L, product, member, "제목", "내용", false,
                                                    "답글", LocalDateTime.now());
    }

    // @Test
    @DisplayName("상품의 전체 상품 문의 조회")
    void testFindALLByProductNo() {
        for (int i = 0; i < 5; i++) {
            ReflectionTestUtils.setField(productInquiryPost, "productInquiryNo", i);
            productInquiryPostRepository.save(productInquiryPost);
        }
        assertThat(productInquiryPostRepository.findAllByProductNo(1L, PageRequest.of(0, 10)))
            .hasSize(5);
    }

    // @Test
    @DisplayName("회원이 남긴 상품 문의 조회")
    void testFindAllByMemberNo() {
        for (int i = 0; i < 5; i++) {
            ReflectionTestUtils.setField(productInquiryPost, "productInquiryNo", i);
            productInquiryPostRepository.save(productInquiryPost);
        }
        assertThat(productInquiryPostRepository.findAllByMemberNo(1L, PageRequest.of(0, 10)))
            .hasSize(5);
    }

}
