package com.xiaozhameng.ssm.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author xiaozhameng
 */
@Controller
public class DemoController {

    /**
     * 为crm 提供的代偿明细查询接口
     */
    @RequestMapping("sayHello/{userName}")
    @ResponseBody
    public String queryCompensatoryDetail(@PathVariable String userName) {
        return "hello," + userName;
    }

    @RequestMapping("demo/test")
    public String demo(Map<String,Object> map){
        map.put("message","this is message");
        return "demo";
    }
}
