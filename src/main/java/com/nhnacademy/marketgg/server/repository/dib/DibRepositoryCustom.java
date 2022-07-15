package com.nhnacademy.marketgg.server.repository.dib;

import com.nhnacademy.marketgg.server.dto.response.DibRetrieveResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface DibRepositoryCustom {

    List<DibRetrieveResponse> findAllDibs(Long memberId);

}
