package com.nhnacademy.marketgg.server.service.file;

import com.nhnacademy.marketgg.server.dto.response.image.ImageResponse;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Image;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    List<Image> parseImages(List<MultipartFile> multipartFiles, Asset asset) throws IOException;

    ImageResponse uploadImage(MultipartFile image) throws IOException;

}
