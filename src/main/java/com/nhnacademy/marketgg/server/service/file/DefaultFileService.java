package com.nhnacademy.marketgg.server.service.file;

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
    private final String option = "NhnStorageService";

    @Override
    public Image uploadImage(final MultipartFile image, final Asset asset) throws IOException {
        Image imageEntity = storageServiceFactory.getService(option).uploadImage(image, asset);
        return imageEntity;
    }

    @Override
    public List<Image> parseImages(List<MultipartFile> multipartFiles, Asset asset) throws IOException {

        List<Image> images = new ArrayList<>();

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            String dir = String.valueOf(Files.createDirectories(returnDir()));
            Integer sequence = 1;

            for (MultipartFile multipartFile : multipartFiles) {
                String filename = uuidFilename(multipartFile.getOriginalFilename());

                File dest = new File(dir, Objects.requireNonNull(filename));
                multipartFile.transferTo(dest);

                String originalFileExtension = "";
                String contentType = multipartFile.getContentType();

                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                }
                if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                }
                Image image = Image.builder().type(originalFileExtension).name(filename).imageAddress(dir)
                                   .length(dest.length()).asset(asset).classification("local").build();
                image.setImageSequence(sequence);
                images.add(image);
                sequence++;
            }
        }

        return images;
    }

    private Path returnDir() {
        String format = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        return Paths.get(DIR, format);
    }

    private String uuidFilename(String filename) {
        return UUID.randomUUID() + "_" + filename;
    }

}
