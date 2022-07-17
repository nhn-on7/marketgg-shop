package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.DibCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.DibDeleteRequest;
import com.nhnacademy.marketgg.server.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Dib;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.exception.dib.DibNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.dib.DibRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.service.DibService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultDibService implements DibService {

    private final DibRepository dibRepository;

    private final MemberRepository memberRepository;

    private final ProductRepository productRepository;

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

    @Transactional
    @Override
    public List<DibRetrieveResponse> retrieveDibs(final Long memberId) {
        return dibRepository.findAllDibs(memberId);
    }

    @Transactional
    @Override
    public void deleteDib(final DibDeleteRequest dibDeleteRequest) {
        Dib dib = dibRepository.findById(new Dib.Pk(dibDeleteRequest.getMemberNo(), dibDeleteRequest.getProductNo()))
                .orElseThrow(DibNotFoundException::new);

        dibRepository.delete(dib);
    }

}
