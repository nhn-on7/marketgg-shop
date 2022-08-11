package com.nhnacademy.marketgg.server.service.file;

import com.nhnacademy.marketgg.server.dto.response.file.ImageResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 마켓 GG 운영에 필요한 파일을 관리하기 위한 '관리' 서비스 입니다.
 * StorageService 빈을 필요에 따라 동적으로 주입받습니다.
 * 이미지 업로드, 이미지 조회 두 가지 기능이 있습니다.
 *
 * @author 조현진
 */
public interface FileService {

    /**
     * 이미지 파일을 저장소에 업로드 한 뒤, 이미지 엔티티를 생성하여 메타 정보를 DB에 따로 저장합니다.
     *
     * @param image - MultipartFile 타입 이미지입니다. 저장소에 올라갈 파일입니다.
     * @return - 이미지의 메타 정보입니다. DB에 들어있는 Image 엔티티의 정보입니다.
     * @throws IOException - IOException을 던집니다.
     */
    ImageResponse uploadImage(final MultipartFile image) throws IOException;

    /**
     * 자원번호를 통해 이미지의 메타 정보를 조회합니다.
     *
     * @param assetId - 자원번호입니다.
     * @return - 이미지의 메타 정보를 반환합니다.
     */
    ImageResponse retrieveImage(final Long assetId);

}
