package com.nhnacademy.marketgg.server.repository.image;

import com.nhnacademy.marketgg.server.dto.response.ImageResponse;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.entity.QImage;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ImageRepositoryImpl extends QuerydslRepositorySupport implements ImageRepositoryCustom {

    public ImageRepositoryImpl() {
        super(Image.class);
    }

    @Override
    public ImageResponse findByAssetId(Long id) {
        QImage image = QImage.image;

        return from(image)
            .where(image.asset.id.eq(id))
            .select(Projections.constructor(ImageResponse.class,
                                            image.name,
                                            image.length,
                                            image.imageAddress,
                                            image.imageSequence))
            .orderBy(image.imageSequence.asc())
            .fetchFirst();
    }
}
