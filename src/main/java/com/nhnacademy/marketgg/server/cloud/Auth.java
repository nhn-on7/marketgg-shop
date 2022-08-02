package com.nhnacademy.marketgg.server.cloud;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Auth {

    private String tenantId;
    private PasswordCredentials passwordCredentials;

}
