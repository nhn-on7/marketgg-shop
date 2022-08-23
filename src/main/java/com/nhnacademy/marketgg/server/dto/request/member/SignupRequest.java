package com.nhnacademy.marketgg.server.dto.request.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class SignupRequest {

    @Email
    @NotBlank
    private String email;

    //'숫자', '문자', '특수문자' 무조건 1개 이상, 비밀번호 '최소 8자에서 최대 16자'까지 허용
    //(특수문자는 정의된 특수문자만 사용 가능)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$")
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
