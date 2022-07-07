package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private static final String CREATE_PRODUCT = "/admin/v1/products";
    private static final String UPDATE_PRODUCT = "/admin/v1/products/{product-id}";

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductCreateRequest productRequest) {
        productService.createProduct(productRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setLocation(URI.create(CREATE_PRODUCT));

        return ResponseEntity.status(HttpStatus.CREATED)
                             .headers(headers)
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @PostMapping("/{product-id}")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductUpdateRequest productRequest,
                                              @PathVariable(name = "product-id") Long productNo) {
        productService.updateProduct(productRequest, productNo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setLocation(URI.create(UPDATE_PRODUCT));

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> retrieveProducts() {
        List<ProductResponse> productList = productService.retrieveProducts();

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productList);
    }

}


