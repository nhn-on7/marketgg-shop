package com.nhnacademy.marketgg.server.cloud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.response.ImageResponse;
import java.io.InputStream;
import java.util.List;

public interface StorageService {

    String requestToken();

    List<String> getContainerList(final String url);

    List<String> getObjectList(final String containerName);

    void uploadObject(final String containerName, final String objectName, final InputStream inputStream)
        throws JsonProcessingException;

    InputStream downloadObject(final String containerName, final String objectName) throws JsonProcessingException;

    ImageResponse retrieveImage(final Long id);
}
