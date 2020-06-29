package com.xiaozhameng.hk.api.service;

import com.xiaozhameng.hk.api.common.Result;
import com.xiaozhameng.hk.api.message.req.DeviceConfigQueryBatchReq;
import com.xiaozhameng.hk.api.message.req.DeviceConfigQueryReq;
import com.xiaozhameng.hk.api.message.req.DeviceLoginReq;
import com.xiaozhameng.hk.api.message.res.DeviceOptCommonRes;
import com.xiaozhameng.hk.api.message.vo.DeviceConfigInfoVo;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;

/**
 * 海康SDK 服务二次封装接口
 *
 * @author xiaozhameng
 * @date 2020/06/26
 * <p>
 * 设备管理相关接口
 */
public interface HkSdkManageService {

    /**
     * 设备配置-新增
     *
     * @param param 请求参数
     * @return vo
     */
    @POST("/api/device/manage/v1/add")
    Result<DeviceOptCommonRes<Boolean>> deviceInfoAdd(@Body DeviceConfigInfoVo param);

    /**
     * 设备配置-修改
     *
     * @param param 请求参数
     * @return vo
     */
    @POST("/api/device/manage/v1/update")
    Result<DeviceOptCommonRes<Boolean>> deviceInfoUpdate(@Body DeviceConfigInfoVo param);

    /**
     * 设备配置-查询
     *
     * @param param 请求参数
     * @return vo
     */
    @POST("/api/device/manage/v1/query")
    Result<DeviceOptCommonRes<DeviceConfigInfoVo>> deviceInfoQuery(@Body DeviceConfigQueryReq param);

    /**
     * 设备配置-批量查询
     *
     * @param param 请求参数
     * @return vo
     */
    @POST("/api/device/manage/v1/query/batch")
    Result<DeviceOptCommonRes<List<DeviceConfigInfoVo>>> deviceInfoQueryBatch(@Body DeviceConfigQueryBatchReq param);

    /**
     * 设备登录，返回登录成功之后的token信息
     *
     * @param param 请求参数
     * @return vo
     */
    @POST("/api/device/manage/v1/login")
    Result<DeviceOptCommonRes<String>> deviceLogin(@Body DeviceLoginReq param);

    /**
     * 设备状态查询，将状态描述信息放在扩展位
     *
     * @param token 请求参数
     * @return vo
     */
    @POST("/api/device/manage/v1/state")
    Result<DeviceOptCommonRes<Boolean>> deviceState(@Body String token);
}
