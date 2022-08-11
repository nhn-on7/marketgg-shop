package com.nhnacademy.marketgg.server.service.storage;

import com.nhnacademy.marketgg.server.dto.request.file.ImageCreateRequest;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 마켓 GG의 파일을 관리하기 위한 Service입니다.
 * 구현체는 로컬, Cloud 두 개가 있습니다.
 * 파일 업로드 단 하나의 기능을 수행합니다.
 * 이후 다운로드 기능이 필요해질 경우 메소드가 추가될 수 있습니다.
 *
 * @author 조현진
 */
public interface StorageService {

    /**
     * MultipartFile 이미지 파일을 저장소에 업로드 한 뒤에, 이미지 엔티티를 생성하기 위한 DTO를 반환합니다.
     * 설정에 따라 로컬 혹은 Object storage에 파일이 저장됩니다.
     *
     * @param image - MultipartFile 입니다.
     * @return - 이미지 엔티티를 만들기 위한 DTO 입니다.
     * @throws IOException - IOException을 던집니다.
     */
    ImageCreateRequest uploadImage(final MultipartFile image) throws IOException;

}
