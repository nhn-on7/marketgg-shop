package com.nhnacademy.marketgg.server.service.file;

import com.nhnacademy.marketgg.server.dto.response.image.ImageResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    ImageResponse uploadImage(final MultipartFile image) throws IOException;

    ImageResponse retrieveImage(final Long assetId);

}
