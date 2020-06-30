package com.xiaozhameng.hk.api.message.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能描述：设备登录成功之后携带token操作
 *
 * @author: xiaozhameng
 * @date: 2020/6/30 6:36 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceOptReq implements Serializable {
    private static final long serialVersionUID = -1651445969074948111L;
    /**
     * 设备Id  ，目前仅适用设备配置ID 就可以登录，后续如果需要安全校验可以扩展
     */
    private String token;
}
