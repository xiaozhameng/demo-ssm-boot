package com.xiaozhameng.hk.api.message.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 设备信息查询接口入参
 * @author xiaozhameng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceConfigQueryReq implements Serializable {
    private static final long serialVersionUID = 8414445468232427025L;
    /**
     * 工位号
     */
    private String stationNo;
    /**
     * 工号
     */
    private String jobNo;
}
