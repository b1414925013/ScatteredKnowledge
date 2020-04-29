package com.jfbian;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class AppTest {

    @Test
    void contextLoads() {
        log.info("测试info");
        log.error("测试error");
        log.debug("测试debug");
    }

}
