package com.xiaozhameng.ssm.boot.service;

import com.xiaozhameng.ssm.boot.service.dao.DeviceInfoDao;
import com.xiaozhameng.ssm.boot.service.dao.po.DeviceInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author xiaozhameng
 */
@Service
public class DeviceInfoService {

    @Resource
    private DeviceInfoDao deviceInfoDao;

    /**
     * 插入一行数据，如果字段为null，则不处理
     *
     * @param deviceInfo deviceInfo
     * @return count
     */
    public int saveSelective(DeviceInfo deviceInfo) {
        return deviceInfoDao.saveSelective(deviceInfo);
    }

    /**
     * 根据主键更新一行数据，如果字段为null，则不处理
     *
     * @param deviceInfo deviceInfo
     * @return count
     */
    public int updateByPrimaryKeySelective(DeviceInfo deviceInfo) {
        return deviceInfoDao.updateByPrimaryKeySelective(deviceInfo);
    }

    /**
     * 根据主键查询一条数据
     *
     * @param id id
     * @return po
     */
    public DeviceInfo getByPrimaryKey(Long id) {
        return deviceInfoDao.getByPrimaryKey(id);
    }

    /**
     * 根据jobNo 和 stationNO 查询一条记录
     * <p>
     * 理论上一个工位和一个工号只能查询到一条记录
     *
     * @param jobNo     工号
     * @param stationNo 工位号
     * @return po
     */
    public DeviceInfo getByJobNoAndStationNo(String jobNo, String stationNo) {
        return deviceInfoDao.getByJobNoAndStationNo(jobNo, stationNo);
    }

    /**
     * 统计
     *
     * @return
     */
    public Long countAll() {
        return Optional.ofNullable(deviceInfoDao.countAll()).orElse(0L);
    }

    /**
     * 简单分页实现
     *
     * @param startId  开始id
     * @param pageSize 页大小
     * @return
     */
    public List<DeviceInfo> getPage(long startId, int pageSize) {
        return deviceInfoDao.getPage(startId, pageSize);
    }

}
