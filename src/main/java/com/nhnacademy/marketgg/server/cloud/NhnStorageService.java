package com.nhnacademy.marketgg.server.cloud;

import static org.springframework.http.HttpMethod.PUT;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class NhnStorageService implements StorageService {

    private final String AUTH_URL = "https://api-identity.infrastructure.cloud.toast.com/v2.0";
    private final String USER_NAME = "computerhermit96@gmail.com";
    private final String PASSWORD = "123!";
    private final String TENANT_ID = "8a2dd42738a0427180466a56561b5eef";
    private final String STORAGE_URL =
        "https://api-storage.cloud.toast.com/v1/AUTH_8a2dd42738a0427180466a56561b5eef";
    private final RestTemplate restTemplate;

    private PasswordCredentials passwordCredentials;
    private Auth auth;
    private TokenRequest tokenRequest;
    private String tokenId;

    @Override
    public String requestToken() {

        passwordCredentials = new PasswordCredentials(USER_NAME, PASSWORD);
        auth = new Auth(TENANT_ID, passwordCredentials);
        tokenRequest = new TokenRequest(auth);

        String identityUrl = this.AUTH_URL + "/tokens";

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<TokenRequest> httpEntity = new HttpEntity<>(this.tokenRequest, headers);

        // 토큰 요청
        ResponseEntity<String> response =
            this.restTemplate.exchange(identityUrl, HttpMethod.POST, httpEntity, String.class);

        return response.getBody();
    }

    @Override
    public List<String> getContainerList(final String url) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", requestToken());

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response =
            this.restTemplate.exchange(url, HttpMethod.GET, requestHttpEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            // String으로 받은 목록을 배열로 변환
            return Arrays.asList(response.getBody().split("\\r?\\n"));
        }

        return Collections.emptyList();
    }

    @Override
    public List<String> getObjectList(final String containerName) {
        return this.getContainerList(this.getUrl(containerName));
    }

    @Override
    public void uploadObject(String containerName, String objectName, final InputStream inputStream) {
        String url = this.getUrl(containerName);

        final RequestCallback requestCallback = request -> {
            request.getHeaders().add("X-Auth-Token", tokenId);
            IOUtils.copy(inputStream, request.getBody());
        };

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpMessageConverterExtractor<String> responseExtractor =
            new HttpMessageConverterExtractor<>(String.class, restTemplate.getMessageConverters());

        restTemplate.execute(url, PUT, requestCallback, responseExtractor);
    }

    private String getUrl(String containerName) {
        return this.STORAGE_URL + "/" + containerName;
    }

}
