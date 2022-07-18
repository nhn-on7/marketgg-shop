package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.response.PointRetrieveResponse;
import com.nhnacademy.marketgg.server.repository.pointhistory.PointHistoryRepository;
import com.nhnacademy.marketgg.server.service.PointService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPointService implements PointService {

    private final PointHistoryRepository pointRepository;

    @Override
    public List<PointRetrieveResponse> retrievePointHistories(final Long id) {
        return pointRepository.findAllByMemberId(id);
    }

    @Override
    public List<PointRetrieveResponse> adminRetrievePointHistories() {
        return pointRepository.findAllForAdmin();
    }
}
