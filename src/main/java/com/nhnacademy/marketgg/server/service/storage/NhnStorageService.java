package com.nhnacademy.marketgg.server.service.storage;

import static org.springframework.http.HttpMethod.PUT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.cloud.Auth;
import com.nhnacademy.marketgg.server.dto.request.cloud.PasswordCredentials;
import com.nhnacademy.marketgg.server.dto.request.cloud.TokenRequest;
import com.nhnacademy.marketgg.server.dto.response.cloud.StorageResponse;
import com.nhnacademy.marketgg.server.dto.response.image.ImageResponse;
import com.nhnacademy.marketgg.server.repository.image.ImageRepository;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class NhnStorageService implements StorageService {


    private final ImageRepository imageRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String HEADER_NAME = "X-Auth-Token";
    private PasswordCredentials passwordCredentials;
    private Auth auth;
    private TokenRequest tokenRequest;
    private StorageResponse storageResponse;

    @Value("${gg.storage.auth-url}")
    private String authUrl;
    @Value("${gg.storage.user-name}")
    private String userName;
    @Value("${gg.storage.password}")
    private String password;
    @Value("${gg.storage.tenant-id}")
    private String tenantId;
    @Value("${gg.storage.storage-url}")
    private String storageUrl;

    @Override
    public String requestToken() {

        passwordCredentials = new PasswordCredentials(userName, password);
        auth = new Auth(tenantId, passwordCredentials);
        tokenRequest = new TokenRequest(auth);

        String identityUrl = this.authUrl + "/tokens";

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
            return Arrays.asList(response.getBody().split("\\r?\\n"));
        }

        return Collections.emptyList();
    }

    @Override
    public List<String> getObjectList(final String containerName) {
        return this.getContainerList(this.getUrl(containerName));
    }

    @Override
    public void uploadObject(final String containerName, String objectName, final InputStream inputStream)
        throws JsonProcessingException {
        String url = this.getUrl(containerName, objectName);
        storageResponse = objectMapper.readValue(requestToken(), StorageResponse.class);
        String tokenId = storageResponse.getAccess().getToken().getId();

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

    @Override
    public InputStream downloadObject(final String containerName, final String objectName)
        throws JsonProcessingException {

        String url = this.getUrl(containerName, objectName);

        storageResponse = objectMapper.readValue(requestToken(), StorageResponse.class);
        String tokenId = storageResponse.getAccess().getToken().getId();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_NAME, tokenId);
        headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<byte[]> response
            = this.restTemplate.exchange(url, HttpMethod.GET, requestHttpEntity, byte[].class);

        return new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public ImageResponse retrieveImage(final Long id) {
        return imageRepository.findByAssetId(id);
    }

    private String getUrl(String containerName, String objectName) {
        return this.storageUrl + "/" + containerName + "/" + objectName;
    }

    private String getUrl(String containerName) {
        return this.storageUrl + "/" + containerName;
    }

}
