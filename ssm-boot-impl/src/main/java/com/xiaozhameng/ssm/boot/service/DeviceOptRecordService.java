package com.xiaozhameng.ssm.boot.service;

import com.xiaozhameng.ssm.boot.service.dao.DeviceOptRecordDao;
import com.xiaozhameng.ssm.boot.service.dao.po.DeviceOptRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xiaozhameng
 */
@Service
public class DeviceOptRecordService {

    @Resource
    private DeviceOptRecordDao deviceOptRecordDao;

    /**
     * 插入一行数据，如果字段为null，则不处理
     *
     * @param deviceOptRecord deviceOptRecord
     * @return count
     */
    public int saveSelective(DeviceOptRecord deviceOptRecord) {
        return deviceOptRecordDao.saveSelective(deviceOptRecord);
    }

    /**
     * 根据主键更新一行数据，如果字段为null，则不处理
     *
     * @param deviceOptRecord deviceOptRecord
     * @return count
     */
    public int updateByPrimaryKeySelective(DeviceOptRecord deviceOptRecord) {
        return deviceOptRecordDao.updateByPrimaryKeySelective(deviceOptRecord);
    }

    /**
     * 根据主键查询一条数据
     *
     * @param id id
     * @return po
     */
    public DeviceOptRecord getByPrimaryKey(Long id) {
        return deviceOptRecordDao.getByPrimaryKey(id);
    }
}
