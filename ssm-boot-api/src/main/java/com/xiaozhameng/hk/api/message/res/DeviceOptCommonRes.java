package com.xiaozhameng.hk.api.message.res;

/**
 * 通用的响应参数
 */
public class DeviceOptCommonRes<T> {
    /**
     * 响应码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 扩展数据
     */
    private T extend;
}
