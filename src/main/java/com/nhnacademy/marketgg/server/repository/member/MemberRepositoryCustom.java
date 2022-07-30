package com.nhnacademy.marketgg.server.repository.member;

import com.nhnacademy.marketgg.server.dto.MemberInfo;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MemberRepositoryCustom {

    Optional<MemberInfo> findMemberInfoByUuid(String uuid);

}
