package com.nhnacademy.marketgg.server.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.server.dto.request.file.ImageCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.file.ImageResponse;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.repository.asset.AssetRepository;
import com.nhnacademy.marketgg.server.repository.image.ImageRepository;
import com.nhnacademy.marketgg.server.service.file.DefaultFileService;
import com.nhnacademy.marketgg.server.service.storage.StorageService;
import com.nhnacademy.marketgg.server.service.storage.StorageServiceFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultFileServiceTest {

    @InjectMocks
    DefaultFileService fileService;

    @Mock
    StorageServiceFactory storageServiceFactory;

    @Mock
    StorageService storageService;

    @Mock
    AssetRepository assetRepository;

    @Mock
    ImageRepository imageRepository;

    private ImageCreateRequest imageCreateRequest;
    private MockMultipartFile imageFile;
    private Asset asset;
    private ImageResponse imageResponse;
    private Image imageEntity;

    @BeforeEach
    void setUp() throws IOException {
        imageCreateRequest =
                ImageCreateRequest.builder().type(".png").imageSequence(1).length(123L).classification("cloud")
                                  .imageAddress("url").name("image name").build();

        URL url = getClass().getClassLoader().getResource("img/lee.png");
        String filePath = Objects.requireNonNull(url).getPath();

        imageFile = new MockMultipartFile("image", "test.png", "image/png", new FileInputStream(filePath));

        asset = Asset.create();

        imageResponse = new ImageResponse("이미지 응답", 1L, "이미지 주소", 1, asset);

        imageEntity = Image.builder().build();
        ReflectionTestUtils.setField(imageEntity, "id", 1L);
    }

    @Test
    @DisplayName("cloud 빈 주입이 제대로 이루어지는지, 주입 이후 클라우드 환경에서 이미지 업로드가 제대로 동작하는지 테스트")
    void testUploadImageOnCloudEnv() throws IOException {
        ReflectionTestUtils.setField(fileService, "option", "nhnStorageService");
        given(storageServiceFactory.getService(anyString())).willReturn(storageService);
        given(storageService.uploadImage(any(MockMultipartFile.class))).willReturn(imageCreateRequest);
        given(assetRepository.save(any(Asset.class))).willReturn(asset);
        given(imageRepository.save(any(Image.class))).willReturn(imageEntity);
        given(imageRepository.queryById(anyLong())).willReturn(imageResponse);

        fileService.uploadImage(imageFile);

        then(storageService).should(times(1)).uploadImage(any(MockMultipartFile.class));

    }

    @Test
    @DisplayName("local 빈 주입이 제대로 이루어지는지, 주입 이후 클라우드 환경에서 이미지 업로드가 제대로 동작하는지 테스트")
    void testUploadImageOnLocalEnv() throws IOException {
        ReflectionTestUtils.setField(fileService, "option", "localStorageService");
        given(storageServiceFactory.getService(anyString())).willReturn(storageService);
        given(storageService.uploadImage(any(MockMultipartFile.class))).willReturn(imageCreateRequest);
        given(assetRepository.save(any(Asset.class))).willReturn(asset);
        given(imageRepository.save(any(Image.class))).willReturn(imageEntity);
        given(imageRepository.queryById(anyLong())).willReturn(imageResponse);

        fileService.uploadImage(imageFile);

        then(storageService).should(times(1)).uploadImage(any(MockMultipartFile.class));

    }

    @Test
    @DisplayName("이미지 조회 테스트")
    void testRetrieveImage() {
        given(imageRepository.queryById(anyLong())).willReturn(imageResponse);

        fileService.retrieveImage(1L);

        then(imageRepository).should(times(1)).queryById(anyLong());
    }

}
