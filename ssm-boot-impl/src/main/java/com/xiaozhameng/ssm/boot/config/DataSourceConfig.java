package com.xiaozhameng.ssm.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置
 * <p>
 * Tag： 这里使用单数据源演示，如果生产环境要使用多数据源，可以通过搜索Spring-boot动态数据源配置
 *
 * 使用分包配置，可以在多数据源情况下扩展配置动态数据源
 *
 * @author xiaozhameng
 */
@Configuration
@PropertySource("classpath:config/application.yml")
@MapperScan(basePackages = "com.xiaozhameng.ssm.boot.service.dao", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    /**
     * 数据源配置的部分参数加到application.yml 中了，部分可以在这里进行扩展
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
        DruidDataSource druidDataSource = DruidDataSourceBuilder.create()
                .build();
        return druidDataSource;
    }

    /**
     * 配置德鲁伊监控，也可以配置在web.xml 文件中，配置在这里的好处是访问控制可以通过外部配置灵活配置
     * <p>
     * 1、配置一个管理后台的Servlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        Map<String, String> initParams = new HashMap<String, String>(16);
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "123456");
        //默认就是允许所有访问
        initParams.put("allow", "");
        initParams.put("deny", "");

        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        bean.setInitParameters(initParams);
        return bean;
    }


    /**
     * 配置德鲁伊监控
     * <p>
     * 2、配置一个web监控的filter
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        Map<String, String> initParams = new HashMap<String, String>(16);
        initParams.put("exclusions", "*.js,*.css,/druid/*");

        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource,
                                               @Qualifier("myBatisPlugins") Interceptor[] myBatisPlugins) throws Exception {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath:mapper/*.xml"));
        sessionFactoryBean.setPlugins(myBatisPlugins);
        return sessionFactoryBean.getObject();
    }


}
