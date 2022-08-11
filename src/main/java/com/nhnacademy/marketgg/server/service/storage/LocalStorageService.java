package com.nhnacademy.marketgg.server.service.storage;

import com.nhnacademy.marketgg.server.dto.request.file.ImageCreateRequest;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Image;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    private static final String DIR = System.getProperty("user.home");

    @Override
    public ImageCreateRequest uploadImage(final MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("이미지가 없습니다.");
        }

        String dir = String.valueOf(Files.createDirectories(returnDir()));
        String type = getContentType(image);
        String fileName = UUID.randomUUID() + type;

        File dest = new File(dir, fileName);
        image.transferTo(dest);

        return ImageCreateRequest.builder()
                                 .name(fileName)
                                 .imageAddress(dir)
                                 .classification("local")
                                 .length(dest.length())
                                 .type(type)
                                 .imageSequence(1)
                                 .build();
    }

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

    // @Override
    // public ImageResponse uploadImage(MultipartFile image) throws IOException {
    //     List<MultipartFile> images = new ArrayList<>();
    //     images.add(image);
    //
    //     Asset asset = assetRepository.save(Asset.create());
    //
    //     List<Image> parseImages = parseImages(images, asset);
    //
    //     imageRepository.saveAll(parseImages);
    //     ImageResponse imageResponse = storageService.retrieveImage(asset.getId());
    //
    //     return imageResponse;
    // }

    private Path returnDir() {
        String format = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        return Paths.get(System.getProperty("user.home"), format);
    }

    private String uuidFilename(String filename) {

        return UUID.randomUUID() + "_" + filename;
    }

    private String getContentType(final MultipartFile image) {
        if (image.getContentType().contains("image/jpeg")) {
            return ".jpg";
        }
        if (image.getContentType().contains("image/png")) {
            return ".png";
        } else {
            throw new IllegalArgumentException("이미지만 업로드할 수 있습니다.");
        }
    }

}
