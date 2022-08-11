package com.nhnacademy.marketgg.server.dto.request.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
