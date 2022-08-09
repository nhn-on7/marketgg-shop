package com.nhnacademy.marketgg.server.repository.dib;

import com.nhnacademy.marketgg.server.dto.response.dib.DibRetrieveResponse;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DibRepositoryCustom {

    /**
     * 회원의 모든 찜 목록을 반환하는 메소드입니다.
     *
     * @param memberId - 회원의 고유 회원 번호 입니다.
     * @return - 회원의 모든 찜 목록을 DTO List 로 반환합니다.
     */
    List<DibRetrieveResponse> findAllDibs(Long memberId);

}
