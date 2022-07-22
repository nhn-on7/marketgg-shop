package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.MemberGradeCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Dib;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.repository.dib.DibRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DibRepositoryTest {

    @Autowired
    private DibRepository dibRepository;

    private static MemberCreateRequest memberCreateRequest;
    private static ProductCreateRequest productCreateRequest;
    private static CategoryCreateRequest categoryCreateRequest;
    private static CategorizationCreateRequest categorizationCreateRequest;


    @BeforeAll
    static void beforeAll() {
        memberCreateRequest = new MemberCreateRequest();
        productCreateRequest = new ProductCreateRequest();
        categoryCreateRequest = new CategoryCreateRequest();
        categorizationCreateRequest = new CategorizationCreateRequest();
    }

    @Test
    @DisplayName("모든 찜 목록 조회")
    void testRetrieveAllDibs() {
        Asset asset = Asset.create();
        Categorization categorization = new Categorization(categorizationCreateRequest);
        Category category = new Category(categoryCreateRequest, categorization);

        Dib dib = new Dib(new Dib.Pk(1L, 1L), new Member(memberCreateRequest), new Product(productCreateRequest, asset, category));

        dibRepository.save(dib);

        List<DibRetrieveResponse> results = dibRepository.findAllDibs(1L);

        assertThat(results).hasSize(1);
    }
}
