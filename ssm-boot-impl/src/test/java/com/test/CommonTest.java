package com.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonTest {

    private static final Logger logger = LoggerFactory.getLogger(CommonTest.class);

    @Test
    public void testRes(){
        logger.info("这是一个日志信息 ， data = {}, item = {}", "kk","pp");
    }
}
