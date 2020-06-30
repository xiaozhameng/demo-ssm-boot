package com.xiaozhameng.hk.api.message.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备相关信息
 * @author xiaozhameng
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeviceConfigInfoVo implements Serializable {
    private static final long serialVersionUID = -8381533202364611257L;
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
