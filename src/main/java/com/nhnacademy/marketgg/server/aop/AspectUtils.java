package com.nhnacademy.marketgg.server.aop;

import javax.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * AOP 편의 Util 클래스입니다.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AspectUtils {

    public static final String AUTH_ID = "AUTH-ID";
    public static final String WWW_AUTHENTICATION = "WWW-Authentication";

    /**
     * 요청 정보를 반환합니다.
     *
     * @return - 사용자 요청정보
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest();
    }

}
