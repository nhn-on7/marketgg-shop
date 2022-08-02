package com.nhnacademy.marketgg.server.cloud;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class NhnAuthService implements AuthService {

    private final String AUTH_URL = "https://api-identity.infrastructure.cloud.toast.com/v2.0";
    private final String USER_NAME = "computerhermit96@gmail.com";
    private final String PASSWORD = "123!";
    private final String TENANT_ID =  "8a2dd42738a0427180466a56561b5eef";
    private final RestTemplate restTemplate;

    private TokenRequest tokenRequest;
    private PasswordCredentials passwordCredentials;
    private Auth auth;

    @Override
    public String requestToken() {

        passwordCredentials = new PasswordCredentials(USER_NAME, PASSWORD);
        auth = new Auth(TENANT_ID, passwordCredentials);
        tokenRequest = new TokenRequest(auth);

        String identityUrl = this.AUTH_URL + "/tokens";

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<TokenRequest> httpEntity
            = new HttpEntity<TokenRequest>(this.tokenRequest, headers);

        // 토큰 요청
        ResponseEntity<String> response
            = this.restTemplate.exchange(identityUrl, HttpMethod.POST, httpEntity, String.class);

        return response.getBody();
    }

}
