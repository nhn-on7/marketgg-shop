package com.nhnacademy.marketgg.server.controller.member;

import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.response.point.PointRetrieveResponse;
import com.nhnacademy.marketgg.server.service.point.PointService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 관련 포인트 내역 관리를 할 수 있는 Mapping 을 지원합니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@RoleCheck(accessLevel = Role.ROLE_USER)
@RestController
@RequestMapping("/members/points")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    private static final String DEFAULT_MEMBER = "/members";

    /**
     * 지정한 회원의 포인트 내역을 반환합니다.
     *
     * @param memberInfo - 지정한 회원의 정보를 담은 객체입니다.
     * @return 지정한 회원의 포인트 내역을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<ShopResult<List<PointRetrieveResponse>>> retrievePointHistory(final MemberInfo memberInfo) {
        List<PointRetrieveResponse> data = pointService.retrievePointHistories(memberInfo.getId());

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_MEMBER + "/points"))
                             .body(ShopResult.success(data));
    }

}
