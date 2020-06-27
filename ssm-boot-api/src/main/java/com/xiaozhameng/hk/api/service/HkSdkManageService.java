package com.xiaozhameng.hk.api.service;

import com.xiaozhameng.hk.api.common.Result;
import com.xiaozhameng.hk.api.message.req.DeviceConfigQueryBatchReq;
import com.xiaozhameng.hk.api.message.req.DeviceConfigQueryReq;
import com.xiaozhameng.hk.api.message.req.DeviceLoginReq;
import com.xiaozhameng.hk.api.message.res.DeviceOptCommonRes;
import com.xiaozhameng.hk.api.message.vo.DeviceConfigInfoVo;

import java.util.List;

/**
 * 海康SDK 服务二次封装接口
 *
 * 设备管理相关接口
 */
public interface HkSdkManageService {

    /**
     * 设备配置-新增
     */
    Result<DeviceOptCommonRes<Boolean>> deviceInfoAdd(DeviceConfigInfoVo param);

    /**
     * 设备配置-修改
     */
    Result<DeviceOptCommonRes<Boolean>> deviceInfoUpdate(DeviceConfigInfoVo param);

    /**
     * 设备配置-查询
     */
    Result<DeviceOptCommonRes<DeviceConfigInfoVo>> deviceInfoQuery(DeviceConfigQueryReq param);

    /**
     * 设备配置-批量查询
     */
    Result<DeviceOptCommonRes<List<DeviceConfigInfoVo>>> deviceInfoQueryBatch(DeviceConfigQueryBatchReq param);

    /**
     * 设备登录，返回登录成功之后的token信息
     */
    Result<DeviceOptCommonRes<String>> deviceLogin(DeviceLoginReq param);

    /**
     * 设备状态查询，将状态描述信息放在扩展位
     */
    Result<DeviceOptCommonRes<String>> deviceState(String token);
}
