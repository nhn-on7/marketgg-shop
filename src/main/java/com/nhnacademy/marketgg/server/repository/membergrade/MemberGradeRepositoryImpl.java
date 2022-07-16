package com.nhnacademy.marketgg.server.repository.membergrade;

import com.nhnacademy.marketgg.server.entity.MemberGrade;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberGradeRepositoryImpl extends QuerydslRepositorySupport implements MemberGradeRepositoryCustom {

    public MemberGradeRepositoryImpl() {
        super(MemberGrade.class);
    }

}
