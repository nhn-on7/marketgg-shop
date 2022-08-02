package com.nhnacademy.marketgg.server.repository.dib;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Dib;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.repository.asset.AssetRepository;
import com.nhnacademy.marketgg.server.repository.cart.CartRepository;
import com.nhnacademy.marketgg.server.repository.categorization.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

// @DataJpaTest
class DibRepositoryTest {

    @Autowired
    DibRepository dibRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    CategorizationRepository categorizationRepository;

    // @Test
    @DisplayName("모든 찜 목록 조회")
    void testRetrieveAllDibs() {
        Asset asset = Asset.create();
        assetRepository.save(asset);

        Categorization categorization = new Categorization(new CategorizationCreateRequest());
        categorizationRepository.save(categorization);

        Category category = new Category(new CategoryCreateRequest(), categorization);
        ReflectionTestUtils.setField(category, "id", "100");
        categoryRepository.save(category);

        Cart savedCart = cartRepository.save(new Cart());
        Member dummyMember = Dummy.getDummyMember(Dummy.DUMMY_UUID, savedCart);
        memberRepository.saveAndFlush(dummyMember);

        Dib dib = new Dib(new Dib.Pk(1L, 1L), dummyMember,
                          new Product(new ProductCreateRequest(), asset, category));

        dibRepository.save(dib);

        List<DibRetrieveResponse> results = dibRepository.findAllDibs(dib.getPk().getMemberNo());

        assertThat(results).hasSize(1);
    }

}
