package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.PointHistoryRequest;
import com.nhnacademy.marketgg.server.dto.response.PointRetrieveResponse;
import java.util.List;

public interface PointService {
    List<PointRetrieveResponse> retrievePointHistories(final Long id);

    List<PointRetrieveResponse> adminRetrievePointHistories();

    void createPointHistory(final Long id, final PointHistoryRequest pointRequest);

    void createPointHistoryForOrder(final Long memberId, final Long orderId, final PointHistoryRequest pointRequest);

}
