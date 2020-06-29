package com.xiaozhameng.ssm.boot.config;

import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;

import java.util.Properties;

/**
 * @author xiaozhameng
 */
@Configuration
public class SftpConfiguration {

    @Value("${sftp-config.sftpHost}")
    private String sftpHost;
    @Value("${sftp-config.port}")
    private String port;
    @Value("${sftp-config.sftpUser}")
    private String sftpUser;
    @Value("${sftp-config.password}")
    private String password;
    @Value("${sftp-config.rootPath}")
    private String sftpRootPath;

    /**
     * sftp 配置
     */
    @Bean
    public SessionFactory<LsEntry> sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost(sftpHost);
        factory.setPort(Integer.parseInt(port));
        factory.setUser(sftpUser);
        factory.setPassword(password);
        return initCachingSessionFactory(factory);
    }

    /**
     * 初始化
     */
    private CachingSessionFactory<LsEntry> initCachingSessionFactory(DefaultSftpSessionFactory factory) {
        Properties jschProps = new Properties();
        // 配置PreferredAuthentications，否则程序控制台会询问user name 和 password。
        jschProps.put("StrictHostKeyChecking", "no");
        jschProps.put("PreferredAuthentications", "password,gssapi-with-mic,publickey,keyboard-interactive");
        factory.setSessionConfig(jschProps);
        factory.setAllowUnknownKeys(true);
        // 设置缓存的属性，缓存和超时时间
        CachingSessionFactory<LsEntry> cachingSessionFactory = new CachingSessionFactory<>(factory);
        cachingSessionFactory.setPoolSize(20);
        cachingSessionFactory.setSessionWaitTimeout(1000);
        return cachingSessionFactory;
    }


    /**
     * sftp template
     */
    @Bean
    public SftpRemoteFileTemplate sftpRemoteFileTemplate() {
        SftpRemoteFileTemplate fileTemplate = new SftpRemoteFileTemplate(sftpSessionFactory());
        fileTemplate.setRemoteDirectoryExpression(new LiteralExpression(sftpRootPath));
        fileTemplate.setAutoCreateDirectory(true);
        fileTemplate.setCharset("UTF-8");
        return fileTemplate;
    }
}
