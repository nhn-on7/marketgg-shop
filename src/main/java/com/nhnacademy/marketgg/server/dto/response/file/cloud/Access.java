package com.nhnacademy.marketgg.server.dto.response.file.cloud;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Access {

    private Token token;

    private User user;

}
