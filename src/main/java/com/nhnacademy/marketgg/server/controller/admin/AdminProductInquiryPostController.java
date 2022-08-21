package com.nhnacademy.marketgg.server.controller.admin;


import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.request.product.ProductInquiryReplyRequest;
import com.nhnacademy.marketgg.server.service.product.ProductInquiryPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
 * @author 민아영
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
     * @author 민아영
     * @since 1.0.0
     */
    @Operation(summary = "상품 문의 답글 등록",
               description = "관리자가 상품 문의 글에 답글을 등록합니다.",
               parameters = @Parameter(name = "replyRequest",
                                       description = "관리자가 작성한 답글이 들어있는 요청 객체", required = true),
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @PutMapping("/inquiry-reply")
    public ResponseEntity<ShopResult<Void>> updateProductInquiryReply(@RequestBody @Valid final
                                                                      ProductInquiryReplyRequest replyRequest) {

        productInquiryPostService.updateProductInquiryReply(replyRequest.getAdminReply()
            , replyRequest.getInquiryId()
            , replyRequest.getProductId());

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.success());
    }

}
