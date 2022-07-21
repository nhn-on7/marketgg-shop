package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ShopMemberSignupRequest {

    private Character gender;
    private Boolean isAdmin;
    private LocalDate birthDate;
    private String uuid;
    private String referrerUuid;

}
