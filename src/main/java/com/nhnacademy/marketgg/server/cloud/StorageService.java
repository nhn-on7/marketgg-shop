package com.nhnacademy.marketgg.server.cloud;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.InputStream;
import java.util.List;

public interface StorageService {

    String requestToken();

    List<String> getContainerList(final String url);

    List<String> getObjectList(final String containerName);

    void uploadObject(String containerName, String objectName, final InputStream inputStream)
        throws JsonProcessingException;
}
