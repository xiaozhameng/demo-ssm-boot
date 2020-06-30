package com.test;

import com.xiaozhameng.ssm.boot.service.dao.po.DeviceInfo;
import com.xiaozhameng.ssm.boot.utils.DateUtils;
import com.xiaozhameng.ssm.boot.utils.TokenUtil;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.time.LocalDateTime;
import java.util.Date;

public class CommonTest {

    @Test
    public void testRes() {

        LocalDateTime localDateTime = DateUtils.dateConvertToLocalDateTime(new Date());
        System.out.println(localDateTime.getYear());
        System.out.println(localDateTime.getMonth().getValue());
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.getHour());
        System.out.println(localDateTime.getMinute());
        System.out.println(localDateTime.getSecond());
    }

    @Test
    public void testTokenGenerate(){
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setId(100L);
        deviceInfo.setJobNo("J10100");
        String generateToken = TokenUtil.generateToken(100L, deviceInfo);
        System.out.println(generateToken);
        System.out.println(TokenUtil.tokenParse(generateToken));

        System.out.println("======");

        String encode = Base64Utils.encodeToString("Token(prefix=D, suffix=X, loginId=100, deviceId=100, jobNo=J10100, temp=null)".getBytes());
        byte[] bytes = Base64Utils.decodeFromString(encode);
        System.out.println(encode);
        System.out.println(new String(bytes));
    }
}
