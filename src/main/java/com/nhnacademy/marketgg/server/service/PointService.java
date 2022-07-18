package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.response.PointRetrieveResponse;
import java.util.List;

public interface PointService {
    List<PointRetrieveResponse> retrievePointHistories(final Long id);

    List<PointRetrieveResponse> adminRetrievePointHistories();

}
