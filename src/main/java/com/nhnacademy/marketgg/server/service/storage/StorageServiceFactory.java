package com.nhnacademy.marketgg.server.service.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * StorageService 빈 주입을 동적으로 하기 위한 팩토리입니다.
 * 상황에 따라 LocalStorageService와 NhnStorageService를 주입합니다.
 *
 * @author 조현진
 */
@Component
public class StorageServiceFactory {

    private final Map<String, StorageService> storagesServices = new HashMap<>();

    /**
     * 스프링 컨테이너가 초기화 될 때 빈을 주입받습니다.
     *
     * @param storageServices - LocalStorageService, NhnStorageService
     */
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
