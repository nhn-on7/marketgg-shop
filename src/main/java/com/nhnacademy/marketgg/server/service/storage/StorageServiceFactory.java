package com.nhnacademy.marketgg.server.service.storage;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * StorageService 빈 주입을 동적으로 하기 위한 팩토리입니다.
 * 상황에 따라 LocalStorageService와 NhnStorageService를 주입합니다.
 *
 * @author 조현진
 */
@Component
@RequiredArgsConstructor
public class StorageServiceFactory {

    private final Map<String, StorageService> storagesServices;

    public StorageService getService(final String name) {
        return this.storagesServices.get(name);
    }
}
