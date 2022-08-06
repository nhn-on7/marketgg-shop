package com.nhnacademy.marketgg.server.repository.member;

import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MemberRepositoryCustom {

    Optional<MemberInfo> findMemberInfoByUuid(String uuid);

    List<Member> findBirthdayMember(String birthday);

}
