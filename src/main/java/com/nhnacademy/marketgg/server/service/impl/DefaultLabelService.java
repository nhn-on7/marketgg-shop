package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.LabelCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.LabelRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.exception.label.LabelNotFoundException;
import com.nhnacademy.marketgg.server.repository.LabelRepository;
import com.nhnacademy.marketgg.server.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 라벨 서비스를 구현한 구현체입니다.
 *
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DefaultLabelService implements LabelService {

    /**
     * 라벨 Repository 입니다.
     *
     * @since 1.0.0
     */
    private final LabelRepository labelRepository;

    /**
     * 입력받은 정보로 라벨을 생성하기 위한 메소드입니다.
     *
     * @param labelCreateRequest - 라벨을 생성하기위한 정보를 담은 DTO 입니다.
     * @since 1.0.0
     */
    @Transactional
    @Override
    public void createLabel(final LabelCreateRequest labelCreateRequest) {
        Label label = new Label(labelCreateRequest);

        labelRepository.save(label);
    }

    /**
     * 전체 라벨 목록을 반환하기 위한 메소드입니다.
     *
     * @return 전체 라벨 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @Override
    public List<LabelRetrieveResponse> retrieveLabels() {
        return labelRepository.findAllLabels();
    }

    /**
     * 지정한 라벨을 삭제하기 위한 메소드입니다.
     *
     * @param id - 삭제할 라벨의 식별번호입니다.
     * @since 1.0.0
     */
    @Transactional
    @Override
    public void deleteLabel(final Long id) {
        Label label = labelRepository.findById(id).orElseThrow(LabelNotFoundException::new);

        labelRepository.delete(label);
    }

}
