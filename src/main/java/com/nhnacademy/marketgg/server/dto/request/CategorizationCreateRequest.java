package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategorizationCreateRequest {

    private String categorizationCode;

    private String name;

    private String alias;

}
