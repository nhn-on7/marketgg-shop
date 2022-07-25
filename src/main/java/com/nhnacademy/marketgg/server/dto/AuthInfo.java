package com.nhnacademy.marketgg.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AuthInfo {

    private String uuid;
    private String email;
    private String name;
    private String phoneNumber;
    private boolean flag;

    public void setUuid(String uuid) {
        if (!flag) {
            this.uuid = uuid;
            flag = true;
        }
    }

}
