package com.xiaozhameng.ssm.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 功能描述：海康威视二次开发页面跳转控制器
 *
 * @author: xiaozhameng
 * @date: 2020/6/28 9:44 上午
 */
@Controller
public class HkSdkManageViewController {

    /**
     * 设备新增页面
     */
    @RequestMapping("/view/device/manage/v1/edit")
    public String deviceConfigAddView(Map<String,Object> map){
        map.put("message","this is message");
        return "deviceConfigAddView";
    }

    /**
     * 设备列表查询页面
     */
    @RequestMapping("/view/device/manage/v1/list/all")
    public String deviceConfigListView(Map<String,Object> map){
        map.put("message","this is message");
        return "deviceConfigListView";
    }

    /**
     * 设备操作页面
     */
    @RequestMapping("/view/device/record/v1/opt")
    public String deviceOptView(Map<String,Object> map){
        map.put("message","this is message");
        return "deviceOptView";
    }
}
