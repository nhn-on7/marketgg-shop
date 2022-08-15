package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.nhnacademy.marketgg.server.dto.request.file.ImageCreateRequest;
import com.nhnacademy.marketgg.server.service.storage.LocalStorageService;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class LocalStorageServiceTest {

    LocalStorageService localStorageService = new LocalStorageService();

    @Test
    @DisplayName("png 파일 업로드 테스트")
    void testLocalUploadPng() throws IOException {
        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();
        MockMultipartFile imageFile =
                new MockMultipartFile("image", "test.png", "image/png", new FileInputStream(filePath));

        ImageCreateRequest imageCreateRequest = localStorageService.uploadImage(imageFile);

        assertThat(imageCreateRequest.getType()).isEqualTo(".png");
        assertThat(imageCreateRequest.getClassification()).isEqualTo("local");
    }

    @Test
    @DisplayName("jpg 파일 업로드 테스트")
    void testLocalUploadJpg() throws IOException {
        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();
        MockMultipartFile imageFile =
                new MockMultipartFile("image", "test.png", "image/jpeg", new FileInputStream(filePath));

        ImageCreateRequest imageCreateRequest = localStorageService.uploadImage(imageFile);

        assertThat(imageCreateRequest.getType()).isEqualTo(".jpg");
        assertThat(imageCreateRequest.getClassification()).isEqualTo("local");
    }

    @Test
    @DisplayName("이미지가 아닐 경우 예외처리 테스트")
    void testLocalUploadNotImageException() throws IOException {
        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();
        MockMultipartFile imageFile =
                new MockMultipartFile("image", "test.png", "text", new FileInputStream(filePath));

        assertThatThrownBy(() -> localStorageService.uploadImage(imageFile)).isInstanceOf(
                IllegalArgumentException.class);
    }

}
