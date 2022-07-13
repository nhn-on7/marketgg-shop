package com.nhnacademy.marketgg.server.service;

public interface MemberService {

    Boolean checkPassUpdatedAt(final Long id);

    void joinPass(final Long id);

    void withdrawPass(final Long id);

}
