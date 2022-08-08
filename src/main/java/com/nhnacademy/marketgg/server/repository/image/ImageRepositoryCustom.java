package com.nhnacademy.marketgg.server.repository.image;

import com.nhnacademy.marketgg.server.dto.response.ImageResponse;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ImageRepositoryCustom {

    ImageResponse findByAssetId(final Long id);
}
