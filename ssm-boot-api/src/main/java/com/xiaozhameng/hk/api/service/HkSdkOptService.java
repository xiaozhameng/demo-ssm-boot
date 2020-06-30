package com.xiaozhameng.hk.api.service;

import com.xiaozhameng.hk.api.common.Result;
import com.xiaozhameng.hk.api.message.req.DeviceOptReq;
import com.xiaozhameng.hk.api.message.req.DeviceRecordOptReq;
import com.xiaozhameng.hk.api.message.res.DeviceOptCommonRes;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 海康SDK 服务二次封装接口
 *
 * @author xiaozhameng
 * @date 2020/06/26
 * <p>
 * 设备操作相关接口
 */
public interface HkSdkOptService {

    /**
     * 开始录像：返回是否录操作成功
     *
     * @param param 请求参数
     * @return vo
     */
    @POST("/compensatory/overdue/calculate")
    Result<DeviceOptCommonRes<Boolean>> videoStart(@Body DeviceOptReq param);

    /**
     * 结束录像：返回是否录操作成功
     *
     * @param param 请求参数
     * @return vo
     */
    @POST("/compensatory/overdue/calculate")
    Result<DeviceOptCommonRes<Boolean>> videoStop(@Body DeviceOptReq param);

    /**
     * 抓图：返回是否操作成功
     *
     * @param param 请求参数
     * @return vo
     */
    @POST("/compensatory/overdue/calculate")
    Result<DeviceOptCommonRes<String>> videoCapture(@Body DeviceOptReq param);

    /**
     * 文件上传：返回是否操作成功
     *
     * @param param 请求参数
     * @return vo
     */
    @POST("/compensatory/overdue/calculate")
    Result<DeviceOptCommonRes<Long>> dataUpload(@Body DeviceRecordOptReq param);

    /**
     * 文件上传进度查询：返回是否操作成功
     *
     * @param param 请求参数
     * @return vo
     * <p>
     * 扩展字段返回上传进度
     */
    @POST("/compensatory/overdue/calculate")
    Result<DeviceOptCommonRes<Long>> dataUploadCheck(@Body DeviceRecordOptReq param);
}
