package com.xiaozhameng.ssm.boot.service.dao;

import com.xiaozhameng.ssm.boot.service.dao.po.DeviceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaozhameng
 */
public interface DeviceInfoDao {

    /**
     * 插入一行数据，如果字段为null，则不处理
     *
     * @param deviceInfo deviceInfo
     * @return count
     */
    int saveSelective(DeviceInfo deviceInfo);

    /**
     * 根据主键更新一行数据，如果字段为null，则不处理
     *
     * @param deviceInfo deviceInfo
     * @return count
     */
    int updateByPrimaryKeySelective(DeviceInfo deviceInfo);

    /**
     * 根据主键查询一条数据
     *
     * @param id id
     * @return po
     */
    DeviceInfo getByPrimaryKey(Long id);

    /**
     * 根据jobNo 和 stationNO 查询一条记录
     *
     * @param jobNo     工号
     * @param stationNo 工位号
     * @return po
     */
    DeviceInfo getByJobNoAndStationNo(@Param("jobNo") String jobNo,@Param("stationNo") String stationNo);

    /**
     * 根据jobNo 和 stationNO 查询一条记录
     *
     * // TODO 这种方式的分页查询，当数据量过大是会造成慢SQL
     *
     * @param startId     开始id
     * @param pageSize 每页的数量
     * @return po
     */
    List<DeviceInfo> getPage(@Param("startId") long startId, @Param("pageSize") int pageSize);

    /**
     * 统计
     * @return
     */
    Long countAll();
}