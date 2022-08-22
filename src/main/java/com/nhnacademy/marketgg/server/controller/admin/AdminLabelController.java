package com.nhnacademy.marketgg.server.controller.admin;

import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.request.label.LabelCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.label.LabelRetrieveResponse;
import com.nhnacademy.marketgg.server.service.label.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 라벨관리에 관련된 RestController 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@RestController
@RequestMapping("/admin/labels")
@RequiredArgsConstructor
public class AdminLabelController {

    private static final String DEFAULT_LABEL = "/admin/labels";

    private final LabelService labelService;

    /**
     * 입력한 정보로 라벨을 등록하는 POST Mapping 을 지원합니다.
     *
     * @param labelCreateRequest - 라벨을 생성하기 위한 DTO 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @Operation(summary = "라벨 등록",
               description = "지정한 값으로 라벨을 생성합니다.",
               parameters = @Parameter(name = "labelCreateRequest", description = "등록할 라벨 정보", required = true),
               responses = @ApiResponse(responseCode = "201",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @PostMapping
    public ResponseEntity<ShopResult<String>> registerLabel(@RequestBody
                                                            @Valid final LabelCreateRequest labelCreateRequest) {

        labelService.createLabel(labelCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_LABEL))
                             .body(ShopResult.successWithDefaultMessage());
    }

    /**
     * 전체 라벨 목록을 조회하는 GET Mapping 을 지원합니다.
     *
     * @return 전체 라벨 목록을 list 로 반환합니다.
     * @since 1.0.0
     */
    @Operation(summary = "라벨 목록조회",
               description = "라벨 목록을 조회합니다.",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @GetMapping
    public ResponseEntity<ShopResult<List<LabelRetrieveResponse>>> retrieveLabels() {
        List<LabelRetrieveResponse> data = labelService.retrieveLabels();

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_LABEL))
                             .body(ShopResult.successWith(data));
    }

    /**
     * 선택한 라벨을 삭제하는 DELETE Mapping 을 지원합니다.
     *
     * @param labelId - 삭제할 라벨의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체입니다.
     * @since 1.0.0
     */
    @Operation(summary = "라벨 삭제",
               description = "지정한 라벨 번호를 삭제합니다.",
               parameters = @Parameter(name = "labelId", description = "라벨 식별번호", required = true),
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @DeleteMapping("/{labelId}")
    public ResponseEntity<ShopResult<String>> deleteLabel(@PathVariable @Min(1) final Long labelId) {
        labelService.deleteLabel(labelId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_LABEL + "/" + labelId))
                             .body(ShopResult.successWithDefaultMessage());
    }

}
