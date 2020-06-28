package com.xiaozhameng.ssm.boot.utils;

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
     *
     * 1位前缀 + 6位随机数 + 设备登录接口返回ID + 设备ID + 后缀
     *
     * @param loginId 设备登录ID
     * @return token
     */
    public static String generateToken(String loginId,long deviceId){
        return null ;
    }


    public static String getDeviceLoginIdFromToken(String token){
        return null ;
    }
}
