package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Util.RedisUtil;
import com.example.demo.Util.RestTemplateUtil;
import com.example.demo.client.MQClient;
import com.example.demo.domain.Info;
import com.example.demo.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class Test {
    private static final Logger log = LoggerFactory.getLogger(Test.class);
    @Autowired
    private TestService testService;
    @Autowired
    private RedisUtil  redisUtil;
    @Autowired
    private MQClient mqClient;

    @PostMapping(value = "/test")
    @ResponseBody
    public Info test(@RequestBody String body ){
        RestTemplateUtil.getResult("","",Boolean.class);
        JSONObject contentJson = JSONObject.parseObject(body);
        int id = contentJson.getInteger("id");
        id = id++;
        log.info("测试" + id);
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


    @RequestMapping("/mqTest")
    public void test(){
        mqClient.send();
    }

}
