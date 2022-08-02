package com.nhnacademy.marketgg.server.cloud;

import java.util.List;

public interface AuthService {

    String requestToken();

    List<String> getContainerList(final String url);

    List<String> getObjectList(final String containerName);


}
