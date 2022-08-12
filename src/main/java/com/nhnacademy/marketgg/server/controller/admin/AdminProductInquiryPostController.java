package com.nhnacademy.marketgg.server.controller.admin;


import com.nhnacademy.marketgg.server.dto.request.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.service.product.ProductInquiryPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 상품 문의 답글 등록을 위한 RestController 입니다.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductInquiryPostController {

    private final ProductInquiryPostService productInquiryPostService;

    /**
     * 상품 문의에 대한 관리자의 답글을 등록하기 위한 PUT Mapping 을 지원합니다.
     *
     * @param inquiryReply - 상품 문의에 대한 관리자의 답글을 담은 DTO 입니다.
     * @param productId    - 상품의 PK 입니다.
     * @param inquiryId    - 업데이트할 상품 문의 글의 PK 입니다.
     * @return - Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PutMapping("/{productId}/inquiries/{inquiryId}")
    public ResponseEntity<CommonResponse> updateProductInquiryReply(@PathVariable final Long productId,
                                                                    @PathVariable final Long inquiryId,
                                                                    @RequestBody final
                                                                    ProductInquiryRequest inquiryReply) {

        productInquiryPostService.updateProductInquiryReply(inquiryReply, inquiryId, productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Add success"));
    }

}
