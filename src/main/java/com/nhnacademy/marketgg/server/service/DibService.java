package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.DibCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.DibDeleteRequest;
import com.nhnacademy.marketgg.server.dto.response.DibRetrieveResponse;

import java.util.List;

public interface DibService {
    
    void createDib(DibCreateRequest dibCreateRequest);

    List<DibRetrieveResponse> retrieveDibs(Long memberNo);

    void deleteDib(DibDeleteRequest dibDeleteRequest);

}
