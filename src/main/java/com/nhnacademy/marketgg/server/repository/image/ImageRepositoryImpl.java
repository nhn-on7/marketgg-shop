package com.nhnacademy.marketgg.server.repository.image;

import com.nhnacademy.marketgg.server.entity.Image;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ImageRepositoryImpl extends QuerydslRepositorySupport implements ImageRepositoryCustom {

    public ImageRepositoryImpl() {
        super(Image.class);
    }

}
