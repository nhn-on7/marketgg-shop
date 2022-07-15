package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.ProductInquiryReply;
import com.nhnacademy.marketgg.server.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.service.ProductInquiryPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/shop/v1")
@RequiredArgsConstructor
public class ProductInquiryPostController {

    private final ProductInquiryPostService productInquiryPostService;

    @Value("${inquiryPath}")
    private String defaultInquiryUri;

    @PostMapping("/products/{productId}/inquiries")
    public ResponseEntity<Void> createProductInquiry(@RequestBody final ProductInquiryRequest productInquiryRequest,
                                                     @PathVariable final Long productId) {

        productInquiryPostService.createProductInquiry(productInquiryRequest, productId);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(defaultInquiryUri + "/products/" + productId + "/inquiries"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @GetMapping("/products/{productId}/inquiries")
    public ResponseEntity<List<ProductInquiryResponse>> retrieveProductInquiryByProductId(@PathVariable final Long productId) {

        List<ProductInquiryResponse> productInquiryResponses = productInquiryPostService
                .retrieveProductInquiryByProductId(productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(defaultInquiryUri + "/products" + productId + "/inquiries"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productInquiryResponses);
    }

    @GetMapping("/mygg/product-inquiries/{memberId}")
    public ResponseEntity<List<ProductInquiryResponse>> retrieveProductInquiryByMemberId(@PathVariable final Long memberId) {

        List<ProductInquiryResponse> productInquiryResponses = productInquiryPostService
                .retrieveProductInquiryByMemberId(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(defaultInquiryUri + "/mygg/product-inquiries/" + memberId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productInquiryResponses);
    }

    @PutMapping("/admin/products/{productId}/inquiries/{inquiryId}")
    public ResponseEntity<Void> updateProductInquiryReply(@RequestBody final ProductInquiryReply inquiryReply,
                                                          @PathVariable final Long productId,
                                                          @PathVariable final Long inquiryId) {

        productInquiryPostService.updateProductInquiryReply(inquiryReply, inquiryId, productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(defaultInquiryUri +
                                     "/admin/products/" + productId + "/inquiries/" + inquiryId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @DeleteMapping("/products/{productId}/inquiries/{inquiryId}")
    public ResponseEntity<Void> deleteProductInquiry(@PathVariable final Long productId,
                                                     @PathVariable final Long inquiryId) {

        productInquiryPostService.deleteProductInquiry(inquiryId, productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(defaultInquiryUri + "/products/" + productId + "/inquiries"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
