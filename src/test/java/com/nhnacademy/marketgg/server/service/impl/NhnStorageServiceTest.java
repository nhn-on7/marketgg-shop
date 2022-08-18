package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.file.ImageCreateRequest;
import com.nhnacademy.marketgg.server.service.storage.NhnStorageService;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class NhnStorageServiceTest {

    @InjectMocks
    NhnStorageService nhnStorageService;

    @Spy
    RestTemplate restTemplate;

    @Spy
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(nhnStorageService,
                                     "authUrl",
                                     "https://api-identity.infrastructure.cloud.toast.com/v2.0");
        ReflectionTestUtils.setField(nhnStorageService, "userName", "computerhermit96@gmail.com");
        ReflectionTestUtils.setField(nhnStorageService, "password", "123!");
        ReflectionTestUtils.setField(nhnStorageService, "tenantId", "8a2dd42738a0427180466a56561b5eef");
        ReflectionTestUtils.setField(nhnStorageService,
                                     "storageUrl",
                                     "https://api-storage.cloud.toast.com/v1/AUTH_8a2dd42738a0427180466a56561b5eef");
    }

    @Test
    @DisplayName("토큰 요청을 제대로 수행하는지 테스트")
    void testRequestToken() {
        String token = nhnStorageService.requestToken();

        assertThat(token).isNotNull();


        then(restTemplate).should(times(1)).exchange(anyString(), any(), any(HttpEntity.class), any(Class.class));
    }

    @Test
    @DisplayName("png 파일 클라우드 업로드 기능을 제대로 수행하는지 테스트")
    void testCloudUploadPng() throws IOException {
        URL url = getClass().getClassLoader().getResource("img/lee.png");
        String filePath = Objects.requireNonNull(url).getPath();
        MockMultipartFile imageFile =
                new MockMultipartFile("image", "test.png", "image/png", new FileInputStream(filePath));

        ImageCreateRequest imageCreateRequest = nhnStorageService.uploadImage(imageFile);

        assertThat(imageCreateRequest.getType()).isEqualTo(".png");
        assertThat(imageCreateRequest.getClassification()).isEqualTo("cloud");
    }

    @Test
    @DisplayName("jpg 파일 클라우드 업로드 기능을 제대로 수행하는지 테스트")
    void testCloudUploadJpg() throws IOException {
        URL url = getClass().getClassLoader().getResource("img/lee.png");
        String filePath = Objects.requireNonNull(url).getPath();
        MockMultipartFile imageFile =
                new MockMultipartFile("image", "test.png", "image/jpeg", new FileInputStream(filePath));

        ImageCreateRequest imageCreateRequest = nhnStorageService.uploadImage(imageFile);

        assertThat(imageCreateRequest.getType()).isEqualTo(".jpg");
        assertThat(imageCreateRequest.getClassification()).isEqualTo("cloud");
    }

    @Test
    @DisplayName("이미지가 아닐 경우 예외처리 테스트")
    void testCloudUploadNotImageException() throws IOException {
        URL url = getClass().getClassLoader().getResource("img/lee.png");
        String filePath = Objects.requireNonNull(url).getPath();
        MockMultipartFile imageFile =
                new MockMultipartFile("image", "test.png", "text", new FileInputStream(filePath));

        assertThatThrownBy(() -> nhnStorageService.uploadImage(imageFile)).isInstanceOf(IllegalArgumentException.class);
    }

}
