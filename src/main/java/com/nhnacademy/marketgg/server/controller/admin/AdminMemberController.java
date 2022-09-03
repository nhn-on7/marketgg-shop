package com.nhnacademy.marketgg.server.controller.admin;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.nhnacademy.marketgg.server.annotation.Auth;
import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.DefaultPageRequest;
import com.nhnacademy.marketgg.server.dto.response.member.AdminMemberResponse;
import com.nhnacademy.marketgg.server.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.server.service.member.MemberService;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Auth
@RestController
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<ShopResult<PageEntity<AdminMemberResponse>>> retrieveMembers(
        final MemberInfo memberInfo, final HttpServletRequest request,
        @RequestParam(value = "page", defaultValue = "1") final Integer page)
        throws UnAuthorizationException {

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!memberInfo.isAdmin() || Objects.isNull(jwt)) {
            throw new UnAuthorizationException();
        }

        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }

        DefaultPageRequest pageRequest = new DefaultPageRequest(page - 1);

        PageEntity<AdminMemberResponse> adminMemberResponses =
            memberService.retrieveMembers(jwt, pageRequest.getPageable());

        return ResponseEntity.status(OK)
                             .contentType(APPLICATION_JSON)
                             .body(ShopResult.successWith(adminMemberResponses));
    }

}
