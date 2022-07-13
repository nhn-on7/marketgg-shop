package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.response.CategorizationRetrieveResponse;
import java.util.List;

public interface CategorizationService {

    List<CategorizationRetrieveResponse> retrieveCategorizations();

}
