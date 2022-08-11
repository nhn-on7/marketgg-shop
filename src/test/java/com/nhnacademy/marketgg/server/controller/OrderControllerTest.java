package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.aop.AuthInjectAspect;
import com.nhnacademy.marketgg.server.aop.MemberInfoAspect;
import com.nhnacademy.marketgg.server.aop.RoleCheckAspect;
import com.nhnacademy.marketgg.server.aop.UuidAspect;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles({"testdb", "common"})
@Import({
        RoleCheckAspect.class,
        AuthInjectAspect.class,
        UuidAspect.class,
        MemberInfoAspect.class
})
public class OrderControllerTest {



}
