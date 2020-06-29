package com.xiaozhameng.ssm.boot.message.result.sdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述：海康SDK 设备状态检查结果
 *
 * @author: xiaozhameng
 * @date: 2020/6/29 12:41 下午
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StateRes {
    private boolean result;
    private String code;
    private String codeDes;
}
