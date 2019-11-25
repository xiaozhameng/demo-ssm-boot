package com.xiaozhameng.ssm.boot.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaozhameng
 */
@RestController
@RequestMapping("/hello")
public class DemoController {

    /**
     * 为crm 提供的代偿明细查询接口
     */
    @RequestMapping("sayHello/{userName}")
    public String queryCompensatoryDetail(@PathVariable String userName) {
        return "hello," + userName;
    }

}
