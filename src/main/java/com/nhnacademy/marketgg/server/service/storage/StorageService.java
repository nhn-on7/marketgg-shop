package com.nhnacademy.marketgg.server.service.storage;

import com.nhnacademy.marketgg.server.dto.request.file.ImageCreateRequest;
import com.nhnacademy.marketgg.server.entity.Asset;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    // String requestToken();
    //
    // List<String> getContainerList(final String url);
    //
    // List<String> getObjectList(final String containerName);
    //
    // void uploadObject(final String containerName, final String objectName, final InputStream inputStream)
    //     throws JsonProcessingException;
    //
    // InputStream downloadObject(final String containerName, final String objectName) throws JsonProcessingException;
    //
    // ImageResponse retrieveImage(final Long id);
    //
    ImageCreateRequest uploadImage(final MultipartFile image) throws IOException;

}
