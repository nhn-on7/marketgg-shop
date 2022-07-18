package com.nhnacademy.marketgg.server.repository.pointhistory;

import com.nhnacademy.marketgg.server.dto.response.PointRetrieveResponse;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PointHistoryRepositoryCustom {

    List<PointRetrieveResponse> findAllByMemberId(final Long id);

    List<PointRetrieveResponse> findAllForAdmin();

}
