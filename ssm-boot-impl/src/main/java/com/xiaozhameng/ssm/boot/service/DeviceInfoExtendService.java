package com.xiaozhameng.ssm.boot.service;

import com.xiaozhameng.ssm.boot.service.dao.DeviceInfoExtendDao;
import com.xiaozhameng.ssm.boot.service.dao.po.DeviceInfoExtend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author xiaozhameng
 */
@Service
public class DeviceInfoExtendService {

    @Resource
    private DeviceInfoExtendDao deviceInfoExtendDao;

    /**
     * 插入一行数据，如果字段为null，则不处理
     *
     * @param deviceInfoExtend deviceInfoExtend
     * @return count
     */
    public int saveSelective(DeviceInfoExtend deviceInfoExtend) {
        return deviceInfoExtendDao.saveSelective(deviceInfoExtend);
    }

    /**
     * 根据主键更新一行数据，如果字段为null，则不处理
     *
     * @param deviceInfoExtend deviceInfoExtend
     * @return count
     */
    public int updateByPrimaryKeySelective(DeviceInfoExtend deviceInfoExtend) {
        return deviceInfoExtendDao.updateByPrimaryKeySelective(deviceInfoExtend);
    }

    /**
     * 根据主键查询一条数据
     *
     * @param id id
     * @return po
     */
    public DeviceInfoExtend getByPrimaryKey(Long id) {
        return deviceInfoExtendDao.getByPrimaryKey(id);
    }

    /**
     * 根据主键查询一条数据
     *
     * @param deviceIds id
     * @return po
     */
    public List<DeviceInfoExtend> getByDeviceIdList(List<Long> deviceIds){
        if (deviceIds == null || deviceIds.isEmpty()){
            return Collections.emptyList();
        }
        return Optional.ofNullable(deviceInfoExtendDao.getByDeviceIdList(deviceIds)).orElse(Collections.emptyList());
    }
}
