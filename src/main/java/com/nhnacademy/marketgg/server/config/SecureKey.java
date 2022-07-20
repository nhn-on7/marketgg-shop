// package com.nhnacademy.marketgg.server.config;
//
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
// import org.springframework.web.client.RestTemplate;
//
// import java.util.Map;
// import java.util.Optional;
//
// @Slf4j
// @Component
// @RequiredArgsConstructor
// public class SecureKey {
//
//     private final RestTemplate restTemplate;
//
//     @Value("${mysql.secret-url}")
//     private String defaultInquiryUri;
//
//     private String getMySqlPassword(String DbSecretUrl) {
//         Map<String, Map<String, String>> response =
//                 restTemplate.getForObject(DbSecretUrl, Map.class);
//
//         return Optional.ofNullable(response)
//                        .orElseThrow(IllegalArgumentException::new)
//                        .get("body")
//                        .get("secret");
//     }
//
// }
