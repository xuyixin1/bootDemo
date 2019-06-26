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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public Info test(@RequestBody String body ){
       // RestTemplateUtil.getResult("","",Boolean.class);
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

    @RequestMapping("/uploadTest1")
    public String test1(MultipartFile file){
        //获取上传文件名,包含后缀
        String originalFilename = file.getOriginalFilename();
        //获取后缀
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        //保存的文件名
        String dFileName = file.getOriginalFilename();
        //保存路径
        //springboot 默认情况下只能加载 resource文件夹下静态资源文件
        String path = "D:/image/";
        //生成保存文件
        File uploadFile = new File(path+dFileName);
        System.out.println(uploadFile);
        //将上传文件保存到路径
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传"+dFileName+"成功";
    }

    @RequestMapping("/uploadTest")
    public Object test1(@RequestParam("file") MultipartFile[] files){
        Map<String ,Object> map = new HashMap<>();
        //获取上传文件名,包含后缀
        for ( MultipartFile file :files ){
            String originalFilename = file.getOriginalFilename();
            //获取后缀
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            //保存的文件名
            String dFileName = file.getOriginalFilename();
            String path = "D:/image/";
            //生成保存文件
            File uploadFile = new File(path+dFileName);
            System.out.println(uploadFile);
            //将上传文件保存到路径
            try {
                file.transferTo(uploadFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map.put("message","sucess");
        return map;
    }
}
