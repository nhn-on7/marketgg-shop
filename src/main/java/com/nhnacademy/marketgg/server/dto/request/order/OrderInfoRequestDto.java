package com.nhnacademy.marketgg.server.dto.request.order;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 배송 서버에 주문 정보를 보내기 위한 DTO 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class OrderInfoRequestDto {

    @Schema(title = "회원 이름", description = "주문한 회원의 이름입니다.", example = "김우식")
    @NotBlank
    private final String receiverName;

    @Schema(title = "주소", description = "주문에 등록된 주소입니다.", example = "경남 김해시 내외중앙로 55")
    @NotBlank
    private final String receiverAddress;

    @Schema(title = "상세 주소", description = "주문의 등록된 상세 주소입니다.", example = "정우빌딩 5층")
    @NotBlank
    private final String receiverDetailAddress;

    @Schema(title = "회원 휴대폰번호", description = "주문하는 회원의 휴대폰 번호입니다.", example = "010-1234-5678")
    @NotBlank
    private final String receiverPhone;

    @Schema(title = "주문 번호", description = "주문의 식별번호입니다.", example = "1L")
    @NotBlank
    private final String orderNo;

    private final String successHost;

}
