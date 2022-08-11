package com.nhnacademy.marketgg.server.service.file;

import com.nhnacademy.marketgg.server.dto.request.file.ImageCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.image.ImageResponse;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.repository.image.ImageRepository;
import com.nhnacademy.marketgg.server.service.storage.StorageServiceFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * 멀티 파일 업로드를 위한 유틸 클래스입니다.
 *
 * @author 조현진
 * @version 1.0.0
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultFileService implements FileService {

    private final StorageServiceFactory storageServiceFactory;
    private final ImageRepository imageRepository;

    private static final String DIR = System.getProperty("user.home");
    //FIXME: 하드코딩 -> 설정에 의해 바뀌도록 리팩토링
    private final String option = "nhnStorageService";

    @Override
    public ImageResponse uploadImage(final MultipartFile image) throws IOException {

        ImageCreateRequest imageCreateRequest = storageServiceFactory.getService(option).uploadImage(image);
        Image imageEntity = Image.builder()
                           .name(imageCreateRequest.getName())
                           .imageAddress(imageCreateRequest.getImageAddress())
                           .type(imageCreateRequest.getType())
                           .asset(Asset.create())
                           .classification(imageCreateRequest.getClassification())
                           .length(imageCreateRequest.getLength())
                           .build();

        imageEntity.setImageSequence(imageCreateRequest.getImageSequence());

        Image saveImage = imageRepository.save(imageEntity);

        return imageRepository.queryById(saveImage.getId());
    }

    @Override
    public ImageResponse retrieveImage(final Long id) {
        return imageRepository.findByAssetId(id);
    }

}
