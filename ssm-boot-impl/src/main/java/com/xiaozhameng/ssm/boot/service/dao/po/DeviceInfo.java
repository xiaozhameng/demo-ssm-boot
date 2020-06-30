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
public class DeviceInfo {

    /** id */
    private Long id;

    /** 设备IP */
    private String ip;

    /** 设备端口号 */
    private Integer port;

    /** 设备登录用户名 */
    private String loginName;

    /** 设备登录密码 */
    private String loginPwd;

    /** 设备安装工位号 */
    private String stationNo;

    /** 员工工号 */
    private String jobNo;

    /** 删除状态 */
    private boolean delFlag;

    /** 创建时间戳 */
    private Date createTime;

    /** 更新时间戳 */
    private Date updateTime;

}
