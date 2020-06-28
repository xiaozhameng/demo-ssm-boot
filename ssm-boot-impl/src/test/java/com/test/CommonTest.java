package com.test;

import com.xiaozhameng.ssm.boot.message.entity.Token;
import com.xiaozhameng.ssm.boot.service.dao.po.DeviceInfo;
import com.xiaozhameng.ssm.boot.utils.TokenUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class CommonTest {

    private static final Logger logger = LoggerFactory.getLogger(CommonTest.class);

    @Test
    public void testRes(){
        DeviceInfo deviceInfo = null ;
        Assert.notNull(deviceInfo,String.format("根据设备ID = %s 未找到设备配置信息，请检查！", 12));
    }
}
