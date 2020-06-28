package com.xiaozhameng.ssm.boot.message.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaozhameng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String prefix;
    private String suffix;
    private long loginId;
    private long deviceId;
    private String temp;
}