package com.zoe.controller;

import com.zoe.common.MyException;
import com.util.spring.resultInfo.ResultData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 陈亚兰 on 2018/6/12.
 */
@RestController
@RequestMapping("/error")
public class ErrorController {
    @GetMapping("/he")
    public ResultData getError(){
        throw new MyException("未经授权");
    }

    @GetMapping("/login")
    public ResultData login(String info){
        throw new MyException(info);
    }

    @GetMapping
    public ResultData getErrorGet(){
        throw new MyException("未经授权且方法不对");
    }

}
