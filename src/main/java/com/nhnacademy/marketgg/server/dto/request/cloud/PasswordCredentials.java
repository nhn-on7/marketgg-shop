package com.nhnacademy.marketgg.server.dto.request.cloud;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordCredentials {

    private String username;
    private String password;

}
