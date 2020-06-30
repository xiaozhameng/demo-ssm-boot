package com.xiaozhameng.hk.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author xiaozhameng
 */

@Getter
@AllArgsConstructor
public enum CommonResCode {

    /**
     * 处理成功
     */
    SUCCESS("success","处理成功"),

    /**
     * 处理事变
     */
    FAILED("failed","处理失败"),

    /**
     * 参数异常
     */
    INVALID_PARAM("invalidParam","参数异常"),

    /**
     * 未知错误
     */
    UNKNOWN("unknown","未知错误");

    private String code;
    private String message;
}
