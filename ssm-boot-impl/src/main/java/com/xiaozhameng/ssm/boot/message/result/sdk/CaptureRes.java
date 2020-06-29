package com.xiaozhameng.ssm.boot.message.result.sdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述：抓图结果封装
 *
 * @author: xiaozhameng
 * @date: 2020/6/29 12:55 下午
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CaptureRes {
    private boolean success;
    private String picPath ;
}
