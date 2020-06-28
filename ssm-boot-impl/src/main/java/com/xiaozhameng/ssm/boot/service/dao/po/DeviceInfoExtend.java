package com.xiaozhameng.ssm.boot.service.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author xiaozhameng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceInfoExtend {

    /** id */
    private Long id;

    /** 设备ID */
    private Long deviceId;

    /** 扩展参数KEY */
    private String key;

    /** 扩展参数值 */
    private String value;

    /** 创建时间戳 */
    private Date createTime;

    /** 更新时间戳 */
    private Date updateTime;

}
