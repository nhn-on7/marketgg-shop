package com.nhnacademy.marketgg.server.service.storage;

import com.nhnacademy.marketgg.server.dto.request.file.ImageCreateRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                                 .build();
    }


    /**
     * 파일의 경로는 매일 달라집니다.
     * 경로는 로그인한 유저의 이름/yyyy-mm-dd 입니다.
     *
     * @return - 파일의 경로를 반환합니다.
     * @author - 조현진
     */
    private Path returnDir() {
        String format = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        return Paths.get(DIR, format);
    }

    /**
     * 파일의 확장자를 관리하기 위한 메소드입니다.
     * 기본적으로 이미지타입이 아닐 경우 Exception을 발생시킵니다.
     *
     * @param image - MultipartFile 타입입니다.
     * @return - 확장자를 반환합니다.
     * @author - 조현진
     */
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
