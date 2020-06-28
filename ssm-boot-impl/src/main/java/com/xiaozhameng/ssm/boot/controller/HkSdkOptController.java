package com.xiaozhameng.ssm.boot.controller;

import com.xiaozhameng.hk.api.common.Result;
import com.xiaozhameng.hk.api.message.req.DeviceRecordOptReq;
import com.xiaozhameng.hk.api.message.res.DeviceOptCommonRes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @author: xiaozhameng
 * @date: 2020/6/27 9:15 下午
 */
@Controller
public class HkSdkOptController {
    
    /**
     * 开始录像：返回是否录操作成功
     *
     * @param token 请求参数
     * @return vo
     */
    @RequestMapping("/compensatory/overdue/calculate")
    public Result<DeviceOptCommonRes<Boolean>> videoStart(@NotNull String token){
        return null;
    }

    /**
     * 结束录像：返回是否录操作成功
     *
     * @param token 请求参数
     * @return vo
     */
    @RequestMapping("/compensatory/overdue/calculate")
    public Result<DeviceOptCommonRes<Boolean>> videoStop(@NotNull String token){
        return null;
    }

    /**
     * 抓图：返回是否操作成功
     *
     * @param token 请求参数
     * @return vo
     */
    @RequestMapping("/compensatory/overdue/calculate")
    public Result<DeviceOptCommonRes<String>> videoCapture(@NotNull String token){
        return null;
    }

    /**
     * 文件上传：返回是否操作成功
     *
     * @param param 请求参数
     * @return vo
     */
    @RequestMapping("/compensatory/overdue/calculate")
    public Result<DeviceOptCommonRes<Boolean>> dataUpload(@Valid @RequestBody DeviceRecordOptReq param){
        return null;
    }

    /**
     * 文件上传进度查询：返回是否操作成功
     *
     * @param param 请求参数
     * @return vo
     * <p>
     * 扩展字段返回上传进度
     */
    @RequestMapping("/compensatory/overdue/calculate")
    public Result<DeviceOptCommonRes<Long>> dataUploadCheck(@Valid @RequestBody DeviceRecordOptReq param){
        return null;
    }
    
}
