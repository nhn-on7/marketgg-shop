package com.nhnacademy.marketgg.server.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.cloud.Access;
import com.nhnacademy.marketgg.server.cloud.DTO;
import com.nhnacademy.marketgg.server.cloud.StorageService;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.service.ImageService;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

    private static final String DIR = System.getProperty("user.home");
    private final StorageService storageService;
    private final ObjectMapper objectMapper;

    @Override
    public List<Image> parseImages(List<MultipartFile> multipartFiles, Asset asset) throws IOException {
        List<Image> images = new ArrayList<>();

        if (CollectionUtils.isEmpty(multipartFiles)) {
            throw new IOException("이미지가 없습니다.");
        }

        // objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String s = storageService.requestToken();
        String test = "{\n" +
            "  \"access\": {\n" +
            "    \"token\": {\n" +
            "      \"id\": \"aaa\",\n" +
            "      \"expires\": \"gd\",\n" +
            "      \"tenant\": {\n" +
            "        \"id\": \"123\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"user\": {\n" +
            "      \"id\": \"123\"\n" +
            "    }\n" +
            "  }\n" +
            "}";

        DTO access = objectMapper.readValue(s, DTO.class);
        log.warn(s);
        log.warn(String.valueOf(access));

        List<String> on7Storage = storageService.getObjectList("on7_storage");
        if (on7Storage != null) {
            for (String s1 : on7Storage) {
                log.warn(s1);
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