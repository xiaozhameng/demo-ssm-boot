package com.xiaozhameng.ssm.boot.controller;

import com.xiaozhameng.hk.api.common.Result;
import com.xiaozhameng.hk.api.message.req.DeviceConfigQueryBatchReq;
import com.xiaozhameng.hk.api.message.req.DeviceConfigQueryReq;
import com.xiaozhameng.hk.api.message.req.DeviceLoginReq;
import com.xiaozhameng.hk.api.message.res.DeviceOptCommonRes;
import com.xiaozhameng.hk.api.message.vo.DeviceConfigInfoVo;
import com.xiaozhameng.ssm.boot.service.DeviceInfoExtendService;
import com.xiaozhameng.ssm.boot.service.DeviceInfoService;
import com.xiaozhameng.ssm.boot.service.dao.po.DeviceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

/**
 * 功能描述：
 *
 * @author: xiaozhameng
 * @date: 2020/6/27 9:13 下午
 */
@RestController
@RequestMapping(value = "/api/device/manage/v1/")
public class HkSdkManageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DeviceInfoExtendService deviceInfoExtendService ;
    @Resource
    private DeviceInfoService deviceInfoService;

    /**
     * 设备配置-新增
     *
     * @param param 请求参数
     * @return vo
     */
    @RequestMapping("add")
    public Result<DeviceOptCommonRes<Boolean>> deviceInfoAdd(@Valid @RequestBody DeviceConfigInfoVo param) {
        DeviceInfo deviceInfo = DeviceInfo.builder().build();
        BeanUtils.copyProperties(param,deviceInfo);
        int saveResult = deviceInfoService.saveSelective(deviceInfo);
        return Result.of(DeviceOptCommonRes.getSuccessInstance(saveResult == 1));
    }

    /**
     * 设备配置-修改
     *
     * @param param 请求参数
     * @return vo
     */
    @RequestMapping("update")
    public Result<DeviceOptCommonRes<Boolean>> deviceInfoUpdate(@Valid @RequestBody DeviceConfigInfoVo param) {
        DeviceInfo deviceInfo = DeviceInfo.builder().build();
        BeanUtils.copyProperties(param,deviceInfo);
        int saveResult = deviceInfoService.updateByPrimaryKeySelective(deviceInfo);
        return Result.of(DeviceOptCommonRes.getSuccessInstance(saveResult == 1));
    }

    /**
     * 设备配置-查询
     *
     * @param param 请求参数
     * @return vo
     */
    @RequestMapping("query")
    public Result<DeviceOptCommonRes<DeviceConfigInfoVo>> deviceInfoQuery(@Valid @RequestBody DeviceConfigQueryReq param) {
        DeviceInfo deviceInfo = deviceInfoService.getByJobNoAndStationNo(param.getJobNo(), param.getStationNo());
        DeviceConfigInfoVo configInfoVo = DeviceConfigInfoVo.builder().build();
        BeanUtils.copyProperties(deviceInfo, configInfoVo);
        return Result.of(DeviceOptCommonRes.getSuccessInstance(configInfoVo));
    }

    /**
     * 设备配置-批量查询
     *
     * @param param 请求参数
     * @return vo
     */
    @RequestMapping("query/batch")
    public Result<DeviceOptCommonRes<List<DeviceConfigInfoVo>>> deviceInfoQueryBatch(@Valid @RequestBody DeviceConfigQueryBatchReq param) {
        List<DeviceInfo> page = deviceInfoService.getPage(0L, 1000);
        List<DeviceConfigInfoVo> result = new LinkedList<>();
        for (DeviceInfo deviceInfo : page) {
            DeviceConfigInfoVo configInfoVo = DeviceConfigInfoVo.builder().build();
            BeanUtils.copyProperties(deviceInfo, configInfoVo);
            result.add(configInfoVo);
        }
        return Result.of(DeviceOptCommonRes.getSuccessInstance(result));
    }

    /**
     * 设备登录，返回登录成功之后的token信息
     *
     * @param param 请求参数
     * @return vo
     */
    @RequestMapping("login")
    public Result<DeviceOptCommonRes<String>> deviceLogin(@Valid @RequestBody DeviceLoginReq param) {
        DeviceInfo deviceInfo = deviceInfoService.getByPrimaryKey(param.getDeviceId());
        Assert.notNull(deviceInfo,String.format("根据设备ID = %s 未找到设备配置信息，请检查！", param.getDeviceId()));
        // 调用设备登录接口


        // 将登录的信息存储在扩展表中

        // 记录设备登录日志

        // 封装结果返回
        return null;
    }

    /**
     * 设备状态查询，将状态描述信息放在扩展位
     *
     * @param token 请求参数
     * @return vo
     */
    @RequestMapping("state")
    public Result<DeviceOptCommonRes<String>> deviceState(@NotNull String token) {
        return null;
    }

}
