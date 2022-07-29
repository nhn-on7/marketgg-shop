package com.nhnacademy.marketgg.server.repository.member;

import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.QMember;
import com.querydsl.core.types.Projections;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberRepositoryImpl extends QuerydslRepositorySupport implements MemberRepositoryCustom {

    public MemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public Optional<MemberInfo> findMemberInfoByUuid(String uuid) {
        QMember member = QMember.member;

        MemberInfo memberInfo = from(member)
            .innerJoin(member.cart)
            .where(member.uuid.eq(uuid))
            .select(Projections.constructor(MemberInfo.class,
                member.id, member.cart, member.memberGrade.grade, member.gender, member.birthDate, member.ggpassUpdatedAt))
            .fetchOne();

        return Optional.ofNullable(memberInfo);
    }

}
