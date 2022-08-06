package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.response.ImageResponse;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.service.ImageService;
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
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 멀티 파일 업로드를 위한 유틸 클래스입니다.
 *
 * @version 1.0.0
 */

@Component
// @Primary
public class LocalImageService implements ImageService {

    private static final String DIR = System.getProperty("user.home");

    /**
     * 다음과 같은 기능을 합니다.
     * - 날짜별 디렉터리 생성
     * - 파일 이름 중복을 피하기 위해 UUID로 변경
     *
     * @param multipartFiles - 컨트롤러로부터 받은 여러장의 사진입니다.
     * @param asset          - 사진을 저장할 자원입니다.
     * @return - 사진 리스트를 반환합니다.
     * @throws IOException - IOException을 발생시킵니다.
     */

    @Override
    public List<Image> parseImages(List<MultipartFile> multipartFiles, Asset asset)
        throws IOException {

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
                Image image = Image.builder()
                                   .type(originalFileExtension)
                                   .name(filename)
                                   .imageAddress(dir)
                                   .length(dest.length())
                                   .asset(asset)
                                   .classification("local")
                                   .build();
                image.setImageSequence(sequence);
                images.add(image);
                sequence++;
            }
        }

        return images;
    }

    @Override
    public ImageResponse uploadImage(MultipartFile image) {

        return null;
    }

    private Path returnDir() {
        String format = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        return Paths.get(DIR, format);
    }

    private String uuidFilename(String filename) {
        return UUID.randomUUID() + "_" + filename;
    }

}
