package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Util.RedisUtil;
import com.example.demo.domain.Info;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class Test {
    @Autowired
    private TestService testService;
    @Autowired
    private RedisUtil  redisUtil;

    @RequestMapping(value = "/test")
    @ResponseBody
    public Info test(@RequestBody String body ){
        JSONObject contentJson = JSONObject.parseObject(body);
        int id = contentJson.getInteger("id");
        id = id++;
        System.out.print(id);
       Info info =  testService.find();
       redisUtil.set("info",info);
       return info;
    }

    @RequestMapping("/insert")
    public void insert()throws Exception{
        testService.insert();
    }

    @RequestMapping("/retry")
    public void retry(){
        testService.retry();
    }


}
