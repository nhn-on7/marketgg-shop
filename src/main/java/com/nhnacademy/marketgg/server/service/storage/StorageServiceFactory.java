package com.nhnacademy.marketgg.server.service.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class StorageServiceFactory {

    private final Map<String, StorageService> storagesServices = new HashMap<>();

    public StorageServiceFactory(List<StorageService> storageServices) {
        if (CollectionUtils.isEmpty(storageServices)) {
            throw new IllegalArgumentException("주입할 StorageService가 없습니다.");
        }

        for (StorageService storageService : storageServices) {
            this.storagesServices.put(storageService.getClass().getSimpleName(), storageService);
        }
    }

    public StorageService getService(String name) {
        return this.storagesServices.get(name);
    }
}
