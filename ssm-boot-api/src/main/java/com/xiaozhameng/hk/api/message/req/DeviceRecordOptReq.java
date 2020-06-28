package com.xiaozhameng.hk.api.message.req;

import java.io.Serializable;

/**
 * 设备操作请求参数
 * @author xiaozhameng
 */
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
