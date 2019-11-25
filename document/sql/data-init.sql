-- 数据库信息

-- 数据库创建语句
create database xiaozhameng if not exists ;

-- 建表语句
USE xiaozhameng;
CREATE TABLE `user_info` (
                           `ID` bigint(20) NOT NULL AUTO_INCREMENT,
                           `USER_ID` VARCHAR(20) DEFAULT ''  COMMENT '用户ID',
                           `USER_NAME` varchar(20) DEFAULT '' COMMENT '用户姓名',
                           `NICK_NAME` varchar(20) DEFAULT '' COMMENT '昵称',
                           `EMAIL` varchar(50) DEFAULT '' COMMENT '邮箱',
                           `SEX` VARCHAR(9) DEFAULT '' COMMENT '性别',
                           `MOBILE` varchar(15) DEFAULT '' COMMENT '手机号',
                           PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='用户表';
