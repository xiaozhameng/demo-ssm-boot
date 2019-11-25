/**
 * <p> Copyright (c) 2015-2025 微聚未来</p>
 * <p>All Rights Reserved. 保留所有权利. </p>
 */

package com.xiaozhameng.ssm.boot.service.dao;


import com.xiaozhameng.ssm.boot.service.dao.po.UserInfo;

/**
 * @author Ge Hui
 */
public interface UserInfoDao {

    /**
     * 插入一行数据，如果字段为null，则不处理
     *
     * @param userInfo userInfo
     * @return count
     */
    int saveSelective(UserInfo userInfo);

    /**
     * 根据主键更新一行数据，如果字段为null，则不处理
     *
     * @param userInfo userInfo
     * @return count
     */
    int updateByPrimaryKeySelective(UserInfo userInfo);

    /**
     * 根据主键查询一条数据
     *
     * @param id id
     * @return po
     */
    UserInfo getByPrimaryKey(Long id);


}