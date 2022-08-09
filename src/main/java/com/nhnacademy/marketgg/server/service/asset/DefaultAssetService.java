package com.nhnacademy.marketgg.server.service.asset;

import com.nhnacademy.marketgg.server.entity.Asset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultAssetService implements AssetService {

    @Override
    @Transactional
    public Asset createAsset(String imageAddress) {
        return null;
    }

}
