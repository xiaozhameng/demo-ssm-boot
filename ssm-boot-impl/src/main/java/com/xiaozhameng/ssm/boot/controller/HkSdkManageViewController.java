package com.xiaozhameng.ssm.boot.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.xiaozhameng.hk.api.message.vo.DeviceConfigInfoVo;
import com.xiaozhameng.ssm.boot.message.enums.DeviceInfoExtendEnum;
import com.xiaozhameng.ssm.boot.message.enums.DeviceOptTypeEnum;
import com.xiaozhameng.ssm.boot.service.DeviceInfoExtendService;
import com.xiaozhameng.ssm.boot.service.DeviceInfoService;
import com.xiaozhameng.ssm.boot.service.dao.po.DeviceInfo;
import com.xiaozhameng.ssm.boot.service.dao.po.DeviceInfoExtend;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 功能描述：海康威视二次开发页面跳转控制器
 *
 * @author: xiaozhameng
 * @date: 2020/6/28 9:44 上午
 */
@Controller
public class HkSdkManageViewController {

    @Resource
    private DeviceInfoService deviceInfoService;
    @Resource
    private DeviceInfoExtendService deviceInfoExtendService;

    /**
     * 实际不要这么查询操作哦，我时间不够用，先这么干了
     * 设备新增页面
     */
    @RequestMapping("/view/device/manage/v1/edit")
    public String deviceConfigAddView(Map<String,Object> mapData){
        this.appendDeviceInfoModel(mapData);
        return "deviceConfigAddView";
    }

    /**
     * 添加设备信息
     * @param mapData
     */
    private void appendDeviceInfoModel(Map<String,Object> mapData){
        // 将设备信息放入到model 中
        List<DeviceConfigInfoVo> modelList = new LinkedList<>();
        List<DeviceInfo> page = deviceInfoService.getPage(0, 1000);
        if (!CollectionUtils.isEmpty(page)){
            List<Long> deviceIds = page.stream().map(DeviceInfo::getId).collect(Collectors.toList());
            List<DeviceInfoExtend> extendList = deviceInfoExtendService.getByDeviceIdList(deviceIds);
            Map<Long, List<DeviceInfoExtend>> groupByDeviceId = new HashMap<>();
            if (extendList != null && !extendList.isEmpty()){
                groupByDeviceId = extendList.stream().collect(Collectors.groupingBy(DeviceInfoExtend::getDeviceId));
            }

            // 参数组装
            for (DeviceInfo deviceInfo : page) {
                DeviceConfigInfoVo item = new DeviceConfigInfoVo();
                BeanUtils.copyProperties(deviceInfo,item);
                List<DeviceInfoExtend> subList = groupByDeviceId.get(deviceInfo.getId());

                if (subList != null && !subList.isEmpty()){
                    Map<String, DeviceInfoExtend> map = subList.stream().collect(Collectors.toMap(DeviceInfoExtend::getKey, Function.identity()));
                    DeviceInfoExtend deviceInfoExtend = map.get(DeviceInfoExtendEnum.TOKEN.name());
                    if (deviceInfoExtend != null){
                        item.setToken(deviceInfoExtend.getValue());
                    }
                }
                modelList.add(item);
            }
        }
        // modelAndView.addObject("deviceList", modelList);
        mapData.put("deviceList", modelList);
    }
}
