package com.zoe.controller;

import com.zoe.common.MyException;
import com.zoe.service.spring.resultInfo.ResultData;
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
        throw new MyException("健康的");
    }
}
