package com.nhnacademy.marketgg.server.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Dib;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.repository.cart.CartRepository;
import com.nhnacademy.marketgg.server.repository.dib.DibRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DibRepositoryTest {

    @Autowired
     DibRepository dibRepository;

    @Autowired
    CartRepository cartRepository;

    @Test
    @DisplayName("모든 찜 목록 조회")
    void testRetrieveAllDibs() {
        Asset asset = Asset.create();
        Categorization categorization = new Categorization(new CategorizationCreateRequest());
        Category category = new Category(new CategoryCreateRequest(), categorization);
        Cart savedCart = cartRepository.save(new Cart());

        Dib dib = new Dib(new Dib.Pk(1L, 1L), new Member(new MemberCreateRequest(), savedCart),
                          new Product(new ProductCreateRequest(), asset, category));

        dibRepository.save(dib);

        List<DibRetrieveResponse> results = dibRepository.findAllDibs(1L);

        assertThat(results).hasSize(1);
    }

}
