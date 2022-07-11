package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
