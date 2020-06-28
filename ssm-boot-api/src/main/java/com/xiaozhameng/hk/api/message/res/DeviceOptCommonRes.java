package com.xiaozhameng.hk.api.message.res;

import com.xiaozhameng.hk.api.enums.CommonResCode;
import lombok.*;

import java.io.Serializable;

/**
 * 通用的响应参数
 *
 * @author xiaozhameng
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeviceOptCommonRes<T> implements Serializable {

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

    /**
     * 返回一个默认成功的结果集
     *
     * @param r   结果集
     * @param <R> 类型
     * @return
     */
    public static <R> DeviceOptCommonRes<R> getSuccessInstance(R r) {
        DeviceOptCommonRes<R> res = new DeviceOptCommonRes<R>();
        res.setCode(CommonResCode.SUCCESS.getCode());
        res.setMessage(CommonResCode.SUCCESS.getMessage());
        res.setExtend(r);
        return res;
    }

    /**
     * 返回一个默认成功的结果集
     *
     * @param <R> 类型
     * @return
     */
    public static <R> DeviceOptCommonRes<R> getFailedInstance() {
        DeviceOptCommonRes<R> res = new DeviceOptCommonRes<R>();
        res.setCode(CommonResCode.FAILED.getCode());
        res.setMessage(CommonResCode.FAILED.getMessage());
        return res;
    }
}
