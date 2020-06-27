package com.xiaozhameng.ssm.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class ThymeleafController {

    @RequestMapping("demo/test")
    public String demo(Map<String,Object> map){
        map.put("message","this is message");
        return "demo";
    }

}
