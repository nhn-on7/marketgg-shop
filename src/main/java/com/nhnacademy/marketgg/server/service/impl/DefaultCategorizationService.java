package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.server.repository.CategorizationRepository;
import com.nhnacademy.marketgg.server.service.CategorizationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 카테고리 분류표 서비스를 구현한 구현체입니다.
 *
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DefaultCategorizationService implements CategorizationService {

    /**
     * 카테고리 분류표 Repository 입니다.
     *
     * @since 1.0.0
     */
    private final CategorizationRepository categorizationRepository;

    /**
     * 전체 카테고리 분류표 목록을 반환하는 메소드입니다.
     *
     * @return 전체 카테고리 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @Override
    public List<CategorizationRetrieveResponse> retrieveCategorizations() {
        return categorizationRepository.findAllCategorization();
    }

}
