package com.nhnacademy.marketgg.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ "testdb", "common" })
@SpringBootTest
class ServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
