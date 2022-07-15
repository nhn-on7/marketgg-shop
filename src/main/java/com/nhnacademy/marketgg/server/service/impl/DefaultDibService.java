package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.DibCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.DibDeleteRequest;
import com.nhnacademy.marketgg.server.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Dib;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.exception.DibNotFoundException;
import com.nhnacademy.marketgg.server.exception.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.dib.DibRepository;
import com.nhnacademy.marketgg.server.repository.MemberRepository;
import com.nhnacademy.marketgg.server.repository.ProductRepository;
import com.nhnacademy.marketgg.server.service.DibService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 찜 서비스를 구현한 구현체입니다.
 *
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DefaultDibService implements DibService {

    private final DibRepository dibRepository;

    private final MemberRepository memberRepository;

    private final ProductRepository productRepository;

    /**
     * 찜을 등록하기 위한 메소드입니다.
     *
     * @param dibCreateRequest - 찜을 등록하기 위한 DTO 입니다.
     * @since 1.0.0
     */
    @Transactional
    @Override
    public void createDib(final DibCreateRequest dibCreateRequest) {
        Member member = memberRepository.findById(dibCreateRequest.getMemberNo())
                                        .orElseThrow(MemberNotFoundException::new);
        Product product = productRepository.findById(dibCreateRequest.getProductNo())
                .orElseThrow(ProductNotFoundException::new);

        Dib dib = new Dib(dibCreateRequest, member, product);

        dibRepository.save(dib);
    }

    /**
     * 회원의 찜 목록을 조회하기 위한 메소드입니다.
     *
     * @param memberId - 찜 목록을 조회할 회원의 고유 번호입니다.
     * @return 회원의 찜 목록을 List 로 반환합니다.
     */
    @Transactional
    @Override
    public List<DibRetrieveResponse> retrieveDibs(final Long memberId) {
        return dibRepository.findAllDibs(memberId);
    }

    /**
     * 찜 목록에서 찜 하나를 삭제하기 위한 메소드입니다.
     *
     * @param dibDeleteRequest - 삭제할 찜의 회원번호와 상품번호가 있는 DTO 입니다.
     */
    @Transactional
    @Override
    public void deleteDib(final DibDeleteRequest dibDeleteRequest) {
        Dib dib = dibRepository.findById(new Dib.Pk(dibDeleteRequest.getMemberNo(), dibDeleteRequest.getProductNo()))
                .orElseThrow(DibNotFoundException::new);

        dibRepository.delete(dib);
    }

}
