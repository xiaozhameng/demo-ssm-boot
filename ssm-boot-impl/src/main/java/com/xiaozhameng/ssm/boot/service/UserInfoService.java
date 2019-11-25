/**
 * <p> Copyright (c) 2015-2025 微聚未来</p>
 * <p>All Rights Reserved. 保留所有权利. </p>
 */

package com.xiaozhameng.ssm.boot.service;


import com.xiaozhameng.ssm.boot.service.dao.UserInfoDao;
import com.xiaozhameng.ssm.boot.service.dao.po.UserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Ge Hui
 */
@Service
public class UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    /**
     * 插入一行数据，如果字段为null，则不处理
     *
     * @param userInfo userInfo
     * @return count
     */
    public int saveSelective(UserInfo userInfo) {
        return userInfoDao.saveSelective(userInfo);
    }

    /**
     * 根据主键更新一行数据，如果字段为null，则不处理
     *
     * @param userInfo userInfo
     * @return count
     */
    public int updateByPrimaryKeySelective(UserInfo userInfo) {
        return userInfoDao.updateByPrimaryKeySelective(userInfo);
    }

    /**
     * 根据主键查询一条数据
     *
     * @param id id
     * @return po
     */
    public UserInfo getByPrimaryKey(Long id) {
        return userInfoDao.getByPrimaryKey(id);
    }

}
