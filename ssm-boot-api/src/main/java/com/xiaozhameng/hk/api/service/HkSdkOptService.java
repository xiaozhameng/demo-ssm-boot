package com.xiaozhameng.hk.api.service;

import com.xiaozhameng.hk.api.common.Result;
import com.xiaozhameng.hk.api.message.req.DeviceRecordOptReq;
import com.xiaozhameng.hk.api.message.res.DeviceOptCommonRes;

/**
 * 海康SDK 服务二次封装接口
 * <p>
 * 设备操作相关接口
 */
public interface HkSdkOptService {

    /**
     * 开始录像：返回是否录操作成功
     */
    Result<DeviceOptCommonRes<Boolean>> videoStart(String token);

    /**
     * 结束录像：返回是否录操作成功
     */
    Result<DeviceOptCommonRes<Boolean>> videoStop(String token);

    /**
     * 抓图：返回是否操作成功
     */
    Result<DeviceOptCommonRes<String>> videoCapture(String token);

    /**
     * 文件上传：返回是否操作成功
     */
    Result<DeviceOptCommonRes<Boolean>> dataUpload(DeviceRecordOptReq param);

    /**
     * 文件上传进度查询：返回是否操作成功
     * <p>
     * 扩展字段返回上传进度
     */
    Result<DeviceOptCommonRes<Long>> dataUploadCheck(DeviceRecordOptReq param);
}
