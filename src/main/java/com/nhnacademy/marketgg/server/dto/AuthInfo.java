package com.nhnacademy.marketgg.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AuthInfo {

    private String email;
    private String name;
    private String phoneNumber;

}
