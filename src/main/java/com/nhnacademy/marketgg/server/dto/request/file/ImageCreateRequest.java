package com.nhnacademy.marketgg.server.dto.request.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * Image 엔티티를 만들기 위한 Request DTO입니다.
 * <p>
 * {@link com.nhnacademy.marketgg.server.service.storage.LocalStorageService#uploadImage(MultipartFile)}
 * {@link com.nhnacademy.marketgg.server.service.storage.NhnStorageService#uploadImage(MultipartFile)}
 *
 * @author 조현진
 */
@NoArgsConstructor
@Getter
public class ImageCreateRequest {

    private String imageAddress;

    private String name;

    private String type;

    private Long length;

    private Integer imageSequence;

    private String classification;

    @Builder
    private ImageCreateRequest(String imageAddress, String name, String type, Long length,
                               Integer imageSequence, String classification) {
        this.imageAddress = imageAddress;
        this.name = name;
        this.type = type;
        this.length = length;
        this.classification = classification;
        this.imageSequence = imageSequence;
    }
}
