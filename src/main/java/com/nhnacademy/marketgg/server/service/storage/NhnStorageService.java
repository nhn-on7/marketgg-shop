package com.nhnacademy.marketgg.server.service.storage;

import static org.springframework.http.HttpMethod.PUT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.file.ImageCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.file.cloud.Auth;
import com.nhnacademy.marketgg.server.dto.request.file.cloud.PasswordCredentials;
import com.nhnacademy.marketgg.server.dto.request.file.cloud.TokenRequest;
import com.nhnacademy.marketgg.server.dto.response.cloud.CloudResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Slf4j
public class NhnStorageService implements StorageService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private PasswordCredentials passwordCredentials;
    private Auth auth;
    private TokenRequest tokenRequest;
    private CloudResponse cloudResponse;

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

    private static final String HEADER_NAME = "X-Auth-Token";
    private static final String DIR = System.getProperty("java.io.tmpdir");

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
    public ImageCreateRequest uploadImage(final MultipartFile image) throws IOException {

        String type = getContentType(image);
        String fileName = UUID.randomUUID() + type;
        File objFile = new File(DIR, Objects.requireNonNull(fileName));
        String url = this.getUrl(fileName);
        image.transferTo(objFile);

        RequestCallback requestCallback;
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpMessageConverterExtractor<String> responseExtractor =
            new HttpMessageConverterExtractor<>(String.class, restTemplate.getMessageConverters());

        try (InputStream inputStream = new FileInputStream(objFile)) {
            cloudResponse = objectMapper.readValue(requestToken(), CloudResponse.class);
            String tokenId = cloudResponse.getAccess().getToken().getId();

            requestCallback = request -> {
                request.getHeaders().add(HEADER_NAME, tokenId);
                IOUtils.copy(inputStream, request.getBody());
            };

            restTemplate.execute(url, PUT, requestCallback, responseExtractor);
            log.info("업로드 성공");

        } catch (IOException e) {
            log.error("error: {}", e.getMessage());
            throw e;
        }

        return ImageCreateRequest.builder()
                                 .name(fileName)
                                 .imageAddress(url)
                                 .classification("cloud")
                                 .imageSequence(1)
                                 .length(objFile.length())
                                 .type(type)
                                 .build();
    }

    private String getUrl(String fileName) {
        return this.storageUrl + "/" + fileName;
    }

    private String getContentType(final MultipartFile image) {
        if (Objects.requireNonNull(image.getContentType()).contains("image/jpeg")) {
            return ".jpg";
        }
        if (Objects.requireNonNull(image.getContentType()).contains("image/png")) {
            return ".png";
        } else {
            throw new IllegalArgumentException("이미지만 업로드할 수 있습니다.");
        }
    }

}
