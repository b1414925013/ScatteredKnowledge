package com.jfbian;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class LogTest {
    @Test
    void test() {
        log.info("这个是info");
        log.error("这个是error");
        log.debug("这个是debug");
        log.warn("这个是warn");
        log.trace("这个是trace");
    }
}
