package com.nhnacademy.marketgg.server.controller.admin;


import com.nhnacademy.marketgg.server.dto.request.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.dto.request.ProductInquiryReplyRequest;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.service.product.ProductInquiryPostService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
     * @param replyRequest - 상품 문의에 대한 관리자의 답글을 담은 DTO 입니다.
     * @return - Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */

    @PutMapping("/inquiry-reply")
    public ResponseEntity<CommonResponse> updateProductInquiryReply(@RequestBody @Valid final
                                                                    ProductInquiryReplyRequest replyRequest) {

        productInquiryPostService.updateProductInquiryReply(replyRequest.getAdminReply()
            , replyRequest.getInquiryId()
            , replyRequest.getProductId());

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Add success"));
    }

}
