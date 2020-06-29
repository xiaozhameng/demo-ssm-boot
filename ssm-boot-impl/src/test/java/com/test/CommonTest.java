package com.test;

import com.xiaozhameng.ssm.boot.utils.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;

public class CommonTest {

    @Test
    public void testRes() {

        LocalDateTime localDateTime = DateUtils.dateConvertToLocalDateTime(new Date());
        System.out.println(localDateTime.getYear());
        System.out.println(localDateTime.getMonth().getValue());
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.getHour());
        System.out.println(localDateTime.getMinute());
        System.out.println(localDateTime.getSecond());
    }
}
