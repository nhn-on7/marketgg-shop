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

    private static final String DEFAULT_PRODUCT = "/admin/v1/products";

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody final ProductCreateRequest productRequest) {
        productService.createProduct(productRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setLocation(URI.create(DEFAULT_PRODUCT));

        return ResponseEntity.status(HttpStatus.CREATED)
                             .headers(headers)
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> retrieveProducts() {
        List<ProductResponse> productList = productService.retrieveProducts();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setLocation(URI.create(DEFAULT_PRODUCT));

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productList);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@RequestBody final ProductUpdateRequest productRequest,
                                              @PathVariable final Long productId) {
        productService.updateProduct(productRequest, productId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setLocation(URI.create(DEFAULT_PRODUCT + "/" + productId.toString()));

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId) {
        productService.deleteProduct(productId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setLocation(URI.create(DEFAULT_PRODUCT + "/" + productId.toString()));

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}


