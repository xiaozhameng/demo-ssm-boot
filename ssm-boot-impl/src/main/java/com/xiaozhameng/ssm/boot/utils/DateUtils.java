package com.xiaozhameng.ssm.boot.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * 功能描述 日期操作工具类
 *
 * @author: xiaozhameng
 * @date: 2020/6/29 8:00 下午
 */
public class DateUtils {

    /**
     * Date 转 LocalDateTime
     */
    public static LocalDateTime dateConvertToLocalDateTime(Date date) {
        return date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
    }


    /**
     * LocalDateTime 转Date
     */
    public static Date localDateTimeConvertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
    }
}
