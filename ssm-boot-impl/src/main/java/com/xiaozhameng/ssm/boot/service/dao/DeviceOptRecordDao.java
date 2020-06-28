package com.xiaozhameng.ssm.boot.service.dao;

import com.xiaozhameng.ssm.boot.service.dao.po.DeviceOptRecord;


/**
 * @author xiaozhameng
 */
public interface DeviceOptRecordDao {

    /**
     * 插入一行数据，如果字段为null，则不处理
     *
     * @param deviceOptRecord deviceOptRecord
     * @return count
     */
    int saveSelective(DeviceOptRecord deviceOptRecord);

    /**
     * 根据主键更新一行数据，如果字段为null，则不处理
     *
     * @param deviceOptRecord deviceOptRecord
     * @return count
     */
    int updateByPrimaryKeySelective(DeviceOptRecord deviceOptRecord);

    /**
     * 根据主键查询一条数据
     *
     * @param id id
     * @return po
     */
    DeviceOptRecord getByPrimaryKey(Long id);


}