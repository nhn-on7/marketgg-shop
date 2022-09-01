package com.nhnacademy.marketgg.server.dto.response.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AdminAuthResponse {

    private Long id;
    private String uuid;
    private String email;
    private String name;
    private String phoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private List<String> roles;

}
