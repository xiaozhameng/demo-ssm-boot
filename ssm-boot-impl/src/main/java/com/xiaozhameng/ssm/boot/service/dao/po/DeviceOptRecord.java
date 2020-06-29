package com.xiaozhameng.ssm.boot.service.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 功能描述：设备操作记录表
 *
 * @author: xiaozhameng
 * @date: 2020/6/28 8:45 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceOptRecord {

    /** id */
    private Long id;

    /** 操作用户用户ID */
    private Long userId;

    /** 用户姓名 */
    private String userName;

    /** 工号 */
    private String jobNo;

    /** 设备ID */
    private long deviceId;

    /** 操作类型 */
    private String optType;

    /** 操作状态 */
    private String state;

    /** 缓存数据 */
    private String optData;

    /** 创建时间戳 */
    private Date createTime;

    /** 更新时间戳 */
    private Date updateTime;

}
