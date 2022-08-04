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
class AdminPostControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("옵션에 따른 게시글 검색")
    void testSearchPostListForOption() throws Exception {
    }

    @Test
    @DisplayName("게시글 수정")
    void testUpdatePost() throws Exception {
    }

    @Test
    @DisplayName("1:1 문의 상태 목록 조회")
    void testRetrieveStatusList() throws Exception {
    }

    @Test
    @DisplayName("1:1 문의 상태 변경")
    void testUpdateInquiryStatus() throws Exception {
    }

}
