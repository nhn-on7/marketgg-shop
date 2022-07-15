package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.DibCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.DibDeleteRequest;
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
import com.nhnacademy.marketgg.server.exception.dib.DibNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.MemberRepository;
import com.nhnacademy.marketgg.server.repository.ProductRepository;
import com.nhnacademy.marketgg.server.repository.dib.DibRepository;
import java.time.LocalDateTime;
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
public class DefaultDibServiceTest {

    @InjectMocks
    DefaultDibService dibService;

    @Mock
    DibRepository dibRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    ProductRepository productRepository;

    private static DibCreateRequest dibCreateRequest;
    private static DibDeleteRequest dibDeleteRequest;
    private static Member member;
    private static Product product;

    @BeforeAll
    static void beforeAll() {
        dibCreateRequest = new DibCreateRequest();
        dibDeleteRequest = new DibDeleteRequest();

        member =
            new Member(new MemberCreateRequest(), new MemberGrade(new MemberGradeCreateRequest()));
        product = new Product(new ProductCreateRequest(), Asset.create(),
            new Category(new CategoryCreateRequest(),
                new Categorization(new CategorizationCreateRequest())));

        ReflectionTestUtils.setField(dibCreateRequest, "memberNo", 1L);
        ReflectionTestUtils.setField(dibCreateRequest, "productNo", 1L);
        ReflectionTestUtils.setField(dibCreateRequest, "memo", "memoSample");
        ReflectionTestUtils.setField(dibCreateRequest, "createdAt", LocalDateTime.now());

        ReflectionTestUtils.setField(dibDeleteRequest, "memberNo", 1L);
        ReflectionTestUtils.setField(dibDeleteRequest, "productNo", 1L);

        ReflectionTestUtils.setField(member, "memberNo", 1L);
        ReflectionTestUtils.setField(product, "productNo", 1L);
    }

    @Test
    @DisplayName("찜 등록 성공")
    void testCreateDibSuccess() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        dibService.createDib(dibCreateRequest);

        verify(memberRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findById(anyLong());
        verify(dibRepository, times(1)).save(any(Dib.class));
    }

    @Test
    @DisplayName("찜 등록 실패(회원 존재 X)")
    void testCreateDibFailWhenMemberNotFound() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dibService.createDib(dibCreateRequest)).isInstanceOf(
            MemberNotFoundException.class);
    }

    @Test
    @DisplayName("찜 등록 실패(상품 존재 X)")
    void testCreateDibFailWhenProductNotFound() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dibService.createDib(dibCreateRequest)).isInstanceOf(
            ProductNotFoundException.class);
    }

    @Test
    @DisplayName("찜 조회 성공")
    void testRetrieveDib() {
        when(dibRepository.findAllDibs(1L)).thenReturn(List.of());

        List<DibRetrieveResponse> dibResponses = dibService.retrieveDibs(1L);

        assertThat(dibResponses).isInstanceOf(List.class);
    }

    @Test
    @DisplayName("찜 삭제 성공")
    void testDeleteDibSuccess() {
        Dib dib = new Dib(dibCreateRequest, member, product);

        when(dibRepository.findById(new Dib.Pk(1L, 1L))).thenReturn(Optional.of(dib));

        doNothing().when(dibRepository).delete(any(Dib.class));

        dibService.deleteDib(dibDeleteRequest);

        verify(dibRepository, times(1)).delete(any(Dib.class));
    }

    @Test
    @DisplayName("찜 삭제 실패(찜 존재 X)")
    void testDeleteDibFailWhenMemberNotFound() {
        when(dibRepository.findById(new Dib.Pk(1L, 1L))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dibService.deleteDib(dibDeleteRequest)).isInstanceOf(
            DibNotFoundException.class);
    }

}
