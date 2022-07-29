package com.nhnacademy.marketgg.server.repository.image;

import com.nhnacademy.marketgg.server.entity.Image;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long>, ImageRepositoryCustom {
    Optional<Image> findByAssetIdAndImageSequence(final Long id, final Integer seq);

}
