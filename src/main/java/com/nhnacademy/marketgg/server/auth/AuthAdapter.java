package com.nhnacademy.marketgg.server.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.MemberNameResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthAdapter implements AuthRepository {

    private String auth = "http://127.0.0.1:7070";

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public List<MemberNameResponse> getNameListByUuid(final List<String> uuidList) throws JsonProcessingException {

        String requestBody = objectMapper.writeValueAsString(uuidList);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, buildHeaders());
        ResponseEntity<List<MemberNameResponse>> response = restTemplate.exchange(
                auth + "/name",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
