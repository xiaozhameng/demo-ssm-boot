package com.xiaozhameng.ssm.boot.utils;

import com.alibaba.fastjson.JSONObject;
import com.xiaozhameng.ssm.boot.message.entity.Token;

/**
 * 功能描述：简单版的token 生成工具
 *
 * @author: xiaozhameng
 * @date: 2020/6/28 2:23 下午
 */
public class TokenUtil {

    private static final String PREFIX = "D";
    private static final String SUFFIX = "X";

    /**
     * token 格式
     * <p>
     * 1位前缀 + 随机数 + 设备登录接口返回ID + 设备ID + 后缀
     *
     * @param loginId 设备登录ID
     * @return token
     */
    public static String generateToken(long loginId, long deviceId) {
        Token token = Token.builder()
                .prefix(PREFIX)
                .suffix(SUFFIX)
                .loginId(loginId)
                .deviceId(deviceId)
                .temp(String.valueOf(System.currentTimeMillis()))
                .build();
        return EncUtil.encrypt(JSONObject.toJSONString(token));
    }

    /**
     * 从token 中解析出想要的数据
     *
     * @param tokenStr token字符串
     * @return vo
     */
    public static Token tokenParse(String tokenStr) {
        String decrypt = EncUtil.decrypt(tokenStr);
        return JSONObject.parseObject(decrypt, Token.class);
    }
}
