package com.nhnacademy.marketgg.server.repository.member;

import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.QMember;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberRepositoryImpl extends QuerydslRepositorySupport implements MemberRepositoryCustom {

    public MemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public Optional<MemberInfo> findMemberInfoByUuid(final String uuid) {
        QMember member = QMember.member;

        MemberInfo memberInfo = from(member)
            .innerJoin(member.cart)
            .where(member.uuid.eq(uuid))
            .select(Projections.constructor(MemberInfo.class,
                member.id, member.cart, member.memberGrade.grade, member.gender, member.birthDate, member.ggpassUpdatedAt))
            .fetchOne();

        return Optional.ofNullable(memberInfo);
    }

    @Override
    public List<Member> findAllMembersByBirthday(final String birthday) {
        QMember member = QMember.member;

        StringTemplate dateFormat
            = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", member.birthDate, ConstantImpl.create("%m-%d"));

        return from(member)
            .where(dateFormat.eq(birthday))
            .fetch();
    }

}
