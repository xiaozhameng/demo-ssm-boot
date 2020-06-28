//package com.xiaozhameng.ssm.boot.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * 控制器
// *
// * @author xiaozhameng
// */
//@RestController
//@RequestMapping("/user")
//public class UserInfoController {
//
//    @Resource
//    private UserInfoService userInfoService;
//
//    /**
//     * 根据userId 查询用户的方法
//     *
//     * @param userId
//     * @return
//     */
//    @RequestMapping(value = "query/{userId}")
//    public String getUserList(@PathVariable long userId) {
//        UserInfo user = userInfoService.getByPrimaryKey(userId);
//        return JSONObject.toJSONString(user);
//    }
//
//}
