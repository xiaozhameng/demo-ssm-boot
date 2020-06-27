package com.xiaozhameng.hk.api.common;

import com.xiaozhameng.hk.api.enums.CommonResCode;

/**
 * 封装公共的响应接口
 */
public final class Result<T> {

    private String code;
    private String message;

    private T data;

    /**
     * 私有化构造器
     *
     * @param code    错误码
     * @param message 错误描述
     * @param data    数据域
     */
    private Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <R> Result<R> of() {
        return new Result<R>(CommonResCode.SUCCESS.getCode(), CommonResCode.SUCCESS.getMessage(), null);
    }

    public static <R> Result<R> of(String code) {
        return new Result<R>(code, null, null);
    }

    public static <R> Result<R> of(CommonResCode code) {
        return new Result<R>(code.getCode(), code.getMessage(), null);
    }

    public static <R> Result<R> of(R res) {
        return new Result<R>(CommonResCode.SUCCESS.getCode(), CommonResCode.SUCCESS.getMessage(), res);
    }

    public static <R> Result<R> of(String code, String message) {
        return new Result<R>(code, message, null);
    }

    public static <R> Result<R> of(String code, String message, R res) {
        return new Result<R>(code, message, res);
    }

    /**
     * 判断是否成功
     *
     * @return code 码比较结果
     */
    public boolean success() {
        return CommonResCode.SUCCESS.getCode().equals(this.code);
    }
}
