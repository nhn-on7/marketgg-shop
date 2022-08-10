package com.nhnacademy.marketgg.server.service.file;

import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Image;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    Image uploadImage(final MultipartFile image, final Asset asset) throws IOException;

}
