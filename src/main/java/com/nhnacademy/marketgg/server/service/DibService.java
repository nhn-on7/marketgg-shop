package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.DibCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.DibDeleteRequest;

public interface DibService {
    
    void createDib(DibCreateRequest dibCreateRequest);

    void deleteDib(DibDeleteRequest dibDeleteRequest);

}
