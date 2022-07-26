package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostDto;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.mapper.impl.CustomerServicePostMapper;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@Transactional
public class DefaultCustomerServicePostServiceTest {

    @InjectMocks
    DefaultCustomerServicePostService customerServicePostService;

    @Mock
    CustomerServicePostRepository customerServicePostRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CustomerServicePostMapper mapper;

    private static CustomerServicePost customerServicePost;
    private static Category category;
    private static Categorization categorization;
    private static Member member;
    private static CustomerServicePostDto customerServicePostDto;
    private static MemberCreateRequest memberCreateRequest;
    private static CategoryCreateRequest categoryCreateRequest;
    private static CategorizationCreateRequest categorizationCreateRequest;

    @BeforeAll
    static void beforeAll() {
        customerServicePostDto = new CustomerServicePostDto();
        memberCreateRequest = new MemberCreateRequest();
        categoryCreateRequest = new CategoryCreateRequest();
        categorizationCreateRequest = new CategorizationCreateRequest();

        member = new Member(memberCreateRequest);
        categorization = new Categorization(categorizationCreateRequest);
        category = new Category(categoryCreateRequest, categorization);
        customerServicePost = new CustomerServicePost(1L, member, category,
                                                      null, null, null, null,
                                                      LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("1:1 문의 등록")
    void testCreateOtoInquiry() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(categoryRepository.findById(anyString())).willReturn(Optional.of(category));
        given(categoryRepository.retrieveCategoryIdByName(anyString())).willReturn(Optional.of("702"));
        given(mapper.toEntity(any(CustomerServicePostDto.class))).willReturn(customerServicePost);

        customerServicePostService.createOtoInquiry(1L, customerServicePostDto);

        then(customerServicePostRepository).should().save(any(CustomerServicePost.class));
    }

    @Test
    @DisplayName("고객센터 게시글 단건 조회")
    void testRetrieveCustomerServicePost() {
        given(customerServicePostRepository.findById(anyLong())).willReturn(Optional.ofNullable(customerServicePost));
        given(mapper.toDto(any(CustomerServicePost.class))).willReturn(customerServicePostDto);

        customerServicePostService.retrieveCustomerServicePost(1L);

        then(customerServicePostRepository).should().findById(anyLong());
    }

    @Test
    @DisplayName("1:1 문의 목록 조회")
    void testRetrieveOtoInquiries() {
        
    }
}
