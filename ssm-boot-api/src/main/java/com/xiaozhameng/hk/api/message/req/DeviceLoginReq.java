package com.xiaozhameng.hk.api.message.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 设备登录接口参数
 * @author xiaozhameng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceLoginReq implements Serializable {
    private static final long serialVersionUID = -4397414757513108867L;

    /**
     * 设备Id  ，目前仅适用设备配置ID 就可以登录，后续如果需要安全校验可以扩展
     */
    private Long deviceId;

}
