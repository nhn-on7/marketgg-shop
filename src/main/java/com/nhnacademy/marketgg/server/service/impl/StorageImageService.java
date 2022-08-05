package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.cloud.StorageService;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.service.ImageService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
@Primary
public class StorageImageService implements ImageService {

    private static final String DIR = "https://api-storage.cloud.toast.com/v1/AUTH_8a2dd42738a0427180466a56561b5eef/on7_storage/";
    private final StorageService storageService;

    @Override
    public List<Image> parseImages(List<MultipartFile> multipartFiles, Asset asset) throws IOException {
        List<Image> images = new ArrayList<>();

        if (CollectionUtils.isEmpty(multipartFiles)) {
            throw new IOException("이미지가 없습니다.");
        }

        String dir = String.valueOf(Files.createDirectories(returnDir()));
        Integer sequence = 1;
        for (MultipartFile multipartFile : multipartFiles) {
            String filename = uuidFilename(multipartFile.getOriginalFilename());
            File objFile = new File(dir, Objects.requireNonNull(filename));

            multipartFile.transferTo(objFile);
            InputStream inputStream = new FileInputStream(objFile);

            String originalFileExtension = "";
            String contentType = multipartFile.getContentType();

            if (contentType.contains("image/jpeg")) {
                originalFileExtension = ".jpg";
            }
            if (contentType.contains("image/png")) {
                originalFileExtension = ".png";
            }
            Image image = Image.builder()
                               .type(originalFileExtension)
                               .name(filename)
                               .imageAddress(DIR)
                               .length(objFile.length())
                               .asset(asset)
                               .classification("storage")
                               .build();
            image.setImageSequence(sequence);
            images.add(image);

            sequence++;
            storageService.uploadObject("on7_storage", filename, inputStream);

        }

        return images;
    }

    private Path returnDir() {
        String format = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        return Paths.get(System.getProperty("user.home"), format);
    }

    private String uuidFilename(String filename) {

        return UUID.randomUUID() + "_" + filename;
    }
}