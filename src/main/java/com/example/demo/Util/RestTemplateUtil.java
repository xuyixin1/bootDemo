package com.example.demo.Util;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class RestTemplateUtil {
    private static final Logger log = LoggerFactory.getLogger(RestTemplateUtil.class);
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=UTF-8";

    @Autowired
    private RestTemplate restTemplateTmp;
    private static RestTemplate restTemplate;

    @PostConstruct
    public void init(){
        restTemplate = restTemplateTmp;
    }


    public static  <T> T getResult(String body,String url, Class<T> t) {
        log.debug("request content :" + body);
        HttpHeaders headers = new HttpHeaders();
        String resultText = post(headers, body,url);
        if (resultText == null || "".equals(resultText)) {
            log.error("response content 为空,body : " + body);
            return null;
        }
        log.debug("response content :" + resultText);
        try {
            return JSONObject.parseObject(resultText, t);
        } catch (Exception e) {
            log.error("解析数据 ===> {} 错误" + resultText);
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private static String post(HttpHeaders headers,String body ,String url) {
        MediaType type = MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8);
        headers.setContentType(type);
        HttpEntity<String> formEntity = new HttpEntity<>(body, headers);
        String result = restTemplate.postForObject(url, formEntity, String.class);
        return result;
    }

    public static void main(String[] args) {
        String url = "http://127.0.0.1:8089/hello/test";
        String body = "{\"id\":1}";
        String s = getResult(body, url, String.class);
        System.out.print(s);

    }




}

