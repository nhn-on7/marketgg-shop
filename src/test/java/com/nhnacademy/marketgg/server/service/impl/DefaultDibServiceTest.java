package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.server.dto.request.category.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.category.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberGradeCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.product.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.dib.DibRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Dib;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.exception.dib.DibNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.dib.DibRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.service.dib.DefaultDibService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultDibServiceTest {

    @InjectMocks
    DefaultDibService dibService;

    @Mock
    DibRepository dibRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    ProductRepository productRepository;

    private static Member member;
    private static Product product;

    @BeforeAll
    static void beforeAll() {
        // TODO: 아래 생성자는 해당 테스트에서만 쓰임 확인 필요.
        member = new Member(new MemberCreateRequest(), new MemberGrade(new MemberGradeCreateRequest()));

        product = new Product(new ProductCreateRequest(), Asset.create(),
                              new Category(new CategoryCreateRequest(),
                                           new Categorization(new CategorizationCreateRequest())));

        ReflectionTestUtils.setField(member, "id", 1L);
        ReflectionTestUtils.setField(product, "id", 1L);
    }

    @Test
    @DisplayName("찜 등록 성공")
    void testCreateDibSuccess() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        dibService.createDib(1L, 1L);

        then(memberRepository).should(times(1)).findById(anyLong());
        then(productRepository).should(times(1)).findById(anyLong());
        then(dibRepository).should(times(1)).save(any(Dib.class));
    }

    @Test
    @DisplayName("찜 등록 실패(회원 존재 X)")
    void testCreateDibFailWhenMemberNotFound() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> dibService.createDib(1L, 1L)).isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("찜 등록 실패(상품 존재 X)")
    void testCreateDibFailWhenProductNotFound() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> dibService.createDib(1L, 1L)).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("찜 조회 성공")
    void testRetrieveDib() {
        given(dibRepository.findAllDibs(1L)).willReturn(List.of());

        List<DibRetrieveResponse> dibResponses = dibService.retrieveDibs(1L);

        assertThat(dibResponses).isInstanceOf(List.class);
    }

    @Test
    @DisplayName("찜 삭제 성공")
    void testDeleteDibSuccess() {
        Dib.Pk pk = new Dib.Pk(member.getId(), product.getId());
        Dib dib = new Dib(pk, member, product);

        given(dibRepository.findById(new Dib.Pk(1L, 1L))).willReturn(Optional.of(dib));

        doNothing().when(dibRepository).delete(any(Dib.class));

        dibService.deleteDib(1L, 1L);

        then(dibRepository).should(times(1)).delete(any(Dib.class));
    }

    @Test
    @DisplayName("찜 삭제 실패(찜 존재 X)")
    void testDeleteDibFailWhenMemberNotFound() {
        given(dibRepository.findById(new Dib.Pk(1L, 1L))).willReturn(Optional.empty());

        assertThatThrownBy(() -> dibService.deleteDib(1L, 1L)).isInstanceOf(DibNotFoundException.class);
    }

}
