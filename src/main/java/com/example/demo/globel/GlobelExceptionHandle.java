package com.example.demo.globel;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobelExceptionHandle {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String  handleException(HttpServletRequest request, Exception e){
        return "系统异常";
    }

}
