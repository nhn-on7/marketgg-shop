package com.nhnacademy.marketgg.server.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;

/**
 * Open API Specification(OAS) 를 위한 Swagger 프레임워크 관련 설정 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@Profile("swagger")
@OpenAPIDefinition(info = @Info(title = "Market GG SHOP-SERVICE",
                                description = "Market GG Shop 서비스에 대한 API 가이드입니다.",
                                contact = @Contact(name = "Contact Me",
                                                   url = "https://github.com/nhn-on7",
                                                   email = "on7.marketgg@gmail.com"),
                                license = @License(name = "Apache License 2.0",
                                                   url = "https://www.apache.org/licenses/LICENSE-2.0"),
                                version = "v1.0.0"))
@Configuration
public class SwaggerConfig {

    /**
     * 그룹핑하고자 하는 API 경로를 지정하고 빈으로 등록합니다.
     * 예를 들어, paths 지역 변수에 "/admin/**" 로 초기화하는 경우 "/admin" 으로 시작하는 경로를 {GROUP_NAME} API 로 그룹핑합니다.
     *
     * @return 그룹핑된 Open API 인스턴스
     */
    @Bean
    public GroupedOpenApi adminGroupedOpenApi() {
        return GroupedOpenApi.builder()
                             .group("관리자 API Specs")
                             .pathsToMatch("/admin/**")
                             .addOpenApiCustomiser(this.openApiCustomizer())
                             .build();
    }

    /**
     * 회원과 관련된 API 경로 목록을 그룹핑하고 빈으로 등록합니다.
     *
     * @return 회원 API 로 그룹핑된 Open API 인스턴스
     */
    @Bean
    public GroupedOpenApi membersGroupedOpenApi() {
        return GroupedOpenApi.builder()
                             .group("회원 API Specs")
                             .pathsToMatch("/members/**")
                             .addOpenApiCustomiser(this.openApiCustomizer())
                             .build();
    }

    /**
     * Swagger 를 통해 API 호출 시 보안 설정과 관련된 부분에 대해 전역 설정이 가능합니다.
     * 해당 메서드는 header 에 JWT 를 주입합니다.
     *
     * @return OpenApiCustomizer
     */
    public OpenApiCustomiser openApiCustomizer() {
        return openApi -> openApi.addSecurityItem(new SecurityRequirement().addList("jwt"))
                                 .getComponents()
                                 .addSecuritySchemes("jwt",
                                                     new SecurityScheme().name(HttpHeaders.AUTHORIZATION)
                                                                         .type(SecurityScheme.Type.HTTP)
                                                                         .in(SecurityScheme.In.HEADER)
                                                                         .bearerFormat("JWT")
                                                                         .scheme("bearer"));
    }

}
