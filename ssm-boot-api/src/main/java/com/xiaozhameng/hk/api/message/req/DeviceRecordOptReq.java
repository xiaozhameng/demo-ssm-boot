package com.xiaozhameng.hk.api.message.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 设备操作请求参数
 * @author xiaozhameng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceRecordOptReq implements Serializable {

    private static final long serialVersionUID = 7422975988828727998L;
    /**
     * 设备登录token
     */
    private String token;

    /**
     * 设备操作记录ID
     */
    private Long recordId ;
}
