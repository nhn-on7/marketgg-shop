package com.nhnacademy.marketgg.server.dto.request.member;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@Getter
public class SignupRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private Character gender;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    private String referrerEmail;

    @NotNull
    private Integer zipcode;

    @NotBlank
    private String address;

    @NotBlank
    private String detailAddress;

    @NotNull
    private boolean inlineRadioOptions;

    private String provider;

    private boolean isAdmin;

    public SignupRequest(final String email,
                         final String password,
                         final String name,
                         final String phoneNumber,
                         final String referrerEmail,
                         final String provider) {

        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.referrerEmail = referrerEmail;
        this.provider = provider;
    }

}
