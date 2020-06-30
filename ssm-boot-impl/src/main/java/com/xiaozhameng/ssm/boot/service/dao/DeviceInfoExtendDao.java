package com.xiaozhameng.ssm.boot.service.dao;

import com.xiaozhameng.ssm.boot.service.dao.po.DeviceInfoExtend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaozhameng
 */
public interface DeviceInfoExtendDao {

    /**
     * 插入一行数据，如果字段为null，则不处理
     *
     * @param deviceInfoExtend deviceInfoExtend
     * @return count
     */
    int saveSelective(DeviceInfoExtend deviceInfoExtend);

    /**
     * 根据主键更新一行数据，如果字段为null，则不处理
     *
     * @param deviceInfoExtend deviceInfoExtend
     * @return count
     */
    int updateByPrimaryKeySelective(DeviceInfoExtend deviceInfoExtend);

    /**
     * 根据主键查询一条数据
     *
     * @param id id
     * @return po
     */
    DeviceInfoExtend getByPrimaryKey(Long id);

    /**
     * 根据主键查询一条数据
     *
     * @param deviceIds id
     * @return po
     */
    List<DeviceInfoExtend> getByDeviceIdList(@Param("deviceIds") List<Long> deviceIds);

}