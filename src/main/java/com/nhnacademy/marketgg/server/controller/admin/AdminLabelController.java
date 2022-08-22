package com.nhnacademy.marketgg.server.controller.admin;

import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.request.label.LabelCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.label.LabelRetrieveResponse;
import com.nhnacademy.marketgg.server.service.label.LabelService;
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
    @DeleteMapping("/{labelId}")
    public ResponseEntity<ShopResult<String>> deleteLabel(@PathVariable @Min(1) final Long labelId) {
        labelService.deleteLabel(labelId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_LABEL + "/" + labelId))
                             .body(ShopResult.successWithDefaultMessage());
    }

}
