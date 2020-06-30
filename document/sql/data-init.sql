drop database if exists hkws;
create database hkws;
use hkws;

-- 创建mysql 用户
create user 'hkws_test'@'%' identified by '123456';
-- mysql 给用户授权
grant all privileges on hkws.* to 'hkws_test'@'%' identified by '123456';

CREATE TABLE `device_opt_record`
(
    `ID`          BIGINT(20) AUTO_INCREMENT,
    `OPT_USER_ID`     BIGINT(20) COMMENT '操作用户用户ID',
    `OPT_USER_NAME`   varchar(32) COMMENT '用户姓名',
    `JOB_NO`      VARCHAR(32) COMMENT '工号',
    `DEVICE_ID`   bigint(32) COMMENT '设备ID',
    `OPT_TYPE`    VARCHAR(20) comment '操作类型',
    `STATE`       VARCHAR(16) COMMENT '操作状态',
    `CREATE_TIME` timestamp not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间戳',
    `UPDATE_TIME` timestamp not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间戳',
    PRIMARY KEY (`ID`),
    KEY `IDX_U_ID` (`OPT_USER_ID`) USING BTREE,
    KEY `IDX_JOB_NO` (`JOB_NO`) USING BTREE,
    KEY `IDX_DEVICE_ID` (`DEVICE_ID`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='设备操作记录表';


CREATE TABLE `device_info`
(
    `ID`          BIGINT(20) AUTO_INCREMENT,
    `IP`          VARCHAR(16) COMMENT '设备IP',
    `PORT`        INT(4) COMMENT '设备端口号',
    `LOGIN_NAME` VARCHAR(32) COMMENT '设备登录用户名',
    `LOGIN_PWD`  varchar(32) COMMENT '设备登录密码',
    `STATION_NO`  VARCHAR(20)        DEFAULT NULL comment '设备安装工位号',
    `JOB_NO`      VARCHAR(32)        DEFAULT NULL COMMENT '员工工号',
    `DEL_FLAG`    tinyint(1) COMMENT '删除状态',
    `CREATE_TIME` timestamp not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间戳',
    `UPDATE_TIME` timestamp not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间戳',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `IDX_IP_PORT` (`IP`, `PORT`) USING BTREE,
    KEY `IDX_JOB_NO` (`JOB_NO`) USING BTREE,
    KEY `IDX_STATION_NO` (`STATION_NO`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='设备信息表';

CREATE TABLE `device_info_extend`
(
    `ID`          BIGINT(20) AUTO_INCREMENT,
    `DEVICE_ID`   BIGINT(20) COMMENT '设备ID',
    `KEY`         VARCHAR(32) COMMENT '扩展参数KEY',
    `VALUE`       VARCHAR(128) COMMENT '扩展参数值',
    `CREATE_TIME` timestamp not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间戳',
    `UPDATE_TIME` timestamp not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间戳',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UNIQ_ID_KEY_VALUE` (`DEVICE_ID`, `KEY`, `VALUE`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='设备信息扩展表，与设备表是一对多关系';
