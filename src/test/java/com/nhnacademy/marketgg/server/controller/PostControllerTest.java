package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.aop.RoleCheckAspect;
import com.nhnacademy.marketgg.server.controller.member.MemberController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

@WebMvcTest(MemberController.class)
@Import({
        RoleCheckAspect.class
})
class PostControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("게시글 등록")
    void testCreatePost() throws Exception {
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void testRetrievePostList() throws Exception {
    }

    @Test
    @DisplayName("게시글 상세 조회")
    void testRetrievePost() throws Exception {
    }

    @Test
    @DisplayName("카테고리 별 게시글 검색")
    void testSearchPostListForCategory() throws Exception {
    }

    @Test
    @DisplayName("게시글 삭제")
    void testDeletePost() throws Exception {
    }

    @Test
    @DisplayName("1:1 문의 사유 목록 조회")
    void testRetrieveReasonList() throws Exception {
    }

}
