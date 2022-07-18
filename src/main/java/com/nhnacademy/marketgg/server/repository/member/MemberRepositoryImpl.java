package com.nhnacademy.marketgg.server.repository.member;

import com.nhnacademy.marketgg.server.entity.Member;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberRepositoryImpl extends QuerydslRepositorySupport implements MemberRepositoryCustom {

    public MemberRepositoryImpl() {
        super(Member.class);
    }

}
