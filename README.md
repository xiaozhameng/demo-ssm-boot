# demo-ssm-boot
一个基于SpringBoot 整合Spring+SpringMVC+Mybatis 搭建的基础框架

## 项目说明
这是一个简单的基于SpringBoot 整合Spring+SpringMVC+Mybatis搭建的web项目

## 部署&启动
如果想在本地部署，启动，需要先搭建数据库，参考 document/sql/data-init.sql 文件中的建库建表语句，
并同步修改application.yml 中的数据源地址配置信息，
启动时直接启动Application.java 中的main方法即可，标准的Spring-boot启动方式，当然也可以使用Tomcat+插件以war包的方式启动


## 访问
启动后，通过http://localhost:8080/userInfo/query/{userId} 即可成功访问

## 后记
其他功能可以依据以上ssm搭建的流程继续完成