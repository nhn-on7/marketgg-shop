package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.DibCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.MemberGradeCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Dib;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.repository.MemberRepository;
import com.nhnacademy.marketgg.server.repository.ProductRepository;
import com.nhnacademy.marketgg.server.repository.dib.DibRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @BeforeAll
    static void beforeAll() {
        dibCreateRequest = new DibCreateRequest();

        ReflectionTestUtils.setField(dibCreateRequest, "memberNo", 1L);
        ReflectionTestUtils.setField(dibCreateRequest, "productNo", 1L);
        ReflectionTestUtils.setField(dibCreateRequest, "memo", "memoSample");
        ReflectionTestUtils.setField(dibCreateRequest, "createdAt", LocalDateTime.now());
    }

    @Test
    @DisplayName("찜 등록 성공")
    void testCreateDibSuccess() {
        Member member = new Member(new MemberCreateRequest(), new MemberGrade(new MemberGradeCreateRequest()));
        Product product = new Product(new ProductCreateRequest(), Asset.create(),
                                      new Category(new CategoryCreateRequest(),
                                                   new Categorization(new CategorizationCreateRequest())));

        ReflectionTestUtils.setField(member, "memberNo", 1L);
        ReflectionTestUtils.setField(product, "productNo", 1L);

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        dibService.createDib(dibCreateRequest);

        verify(memberRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findById(anyLong());
        verify(dibRepository, times(1)).save(any(Dib.class));
    }

}
