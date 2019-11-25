package com.xiaozhameng.ssm.boot.config;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 公共的配置项
 *
 * @author xiaozhameng
 */
@Configuration
public class CommonConfiguration {

    @Bean(name = "myBatisPlugins")
    public Interceptor[] myBatisPlugins(){
        return new Interceptor[]{
                // 这里可以自定义mybatis插件
        };
    }
}
