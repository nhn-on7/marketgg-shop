package com.nhnacademy.marketgg.server.dto.info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class AuthInfo {

    private String email;
    private String name;
    private String phoneNumber;

}
