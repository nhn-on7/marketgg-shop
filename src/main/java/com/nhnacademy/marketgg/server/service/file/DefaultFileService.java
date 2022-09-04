package com.nhnacademy.marketgg.server.service.file;

import com.nhnacademy.marketgg.server.dto.request.file.ImageCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.file.ImageResponse;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.repository.asset.AssetRepository;
import com.nhnacademy.marketgg.server.repository.image.ImageRepository;
import com.nhnacademy.marketgg.server.service.storage.StorageServiceFactory;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultFileService implements FileService {

    private final StorageServiceFactory storageServiceFactory;
    private final ImageRepository imageRepository;
    private final AssetRepository assetRepository;

    private static final String DIR = System.getProperty("user.home");
    @Value(("${gg.storage.option}"))
    private String option;

    @Override
    public ImageResponse uploadImage(final MultipartFile image) throws IOException {

        ImageCreateRequest imageCreateRequest = storageServiceFactory.getService(option).uploadImage(image);
        Asset asset = assetRepository.save(Asset.create());
        Image imageEntity = Image.builder()
                                 .name(imageCreateRequest.getName())
                                 .imageAddress(imageCreateRequest.getImageAddress())
                                 .type(imageCreateRequest.getType())
                                 .asset(asset)
                                 .classification(imageCreateRequest.getClassification())
                                 .length(imageCreateRequest.getLength())
                                 .build();

        Image saveImage = imageRepository.save(imageEntity);

        return imageRepository.queryById(saveImage.getId());
    }

    @Override
    public ImageResponse retrieveImage(final Long id) {
        return imageRepository.queryById(id);
    }

}
