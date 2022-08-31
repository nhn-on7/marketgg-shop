package com.nhnacademy.marketgg.server.controller.file;

import com.nhnacademy.marketgg.server.dto.response.file.ImageResponse;
import com.nhnacademy.marketgg.server.service.file.FileService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일을 관리하기 위한 controller입니다.
 *
 * @author 조현진
 */
@Slf4j
@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 이미지를 조회합니다.
     *
     * @param assetId - 자원번호입니다.
     * @return 이미지 응답객체를 반환합니다.
     */
    @GetMapping("/{assetId}")
    public ResponseEntity<ImageResponse> retrieveImage(@PathVariable final Long assetId) {

        ImageResponse imageResponse = fileService.retrieveImage(assetId);

        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }

    /**
     * Toast UI를 사용해서 사진 등록을 하는 경우 이 메소드가 실행됩니다.
     * 사진 등록을 누르는 순간 해당 사진이 Storage로 업로드 된 후, 해당 사진의 메타 데이터를 담은 응답객체를 반환합니다.
     * 응답객체엔 사진의 url, 이름이 담겨있습니다.
     *
     * @param image - 이미지 파일입니다.
     * @return - 이미지의 메타데이터를 담은 응답객체를 반환합니다.
     * @throws IOException - IOException을 던집니다.
     */
    @PostMapping
    public ResponseEntity<ImageResponse> uploadAndRetrieveImage(@RequestBody final MultipartFile image)
            throws IOException {

        ImageResponse imageResponse = fileService.uploadImage(image);

        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }

}

