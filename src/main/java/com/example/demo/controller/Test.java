package com.example.demo.controller;

import com.example.demo.Util.RedisUtil;
import com.example.demo.domain.Info;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class Test {
    @Autowired
    private TestService testService;
    @Autowired
    private RedisUtil  redisUtil;
    @RequestMapping("/test")
    @ResponseBody
    public Info test(){
       Info info =  testService.find();
       redisUtil.set("info",info);
       return info;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public void insert()throws Exception{
        testService.insert();
    }


}
