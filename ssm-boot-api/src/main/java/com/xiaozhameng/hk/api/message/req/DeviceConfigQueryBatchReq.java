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
public class DeviceConfigQueryBatchReq implements Serializable {
    private static final long serialVersionUID = -5341564954717373431L;
    /**
     * 页码
     */
    private int pageNo ;
    /**
     * 页大小
     */
    private int pageSize;
}
