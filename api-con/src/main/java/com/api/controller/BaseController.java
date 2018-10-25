package com.api.controller;

import com.util.spring.resultInfo.ResultData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lan.Chen on 2018/10/25
 */
@RestController
@RequestMapping("/base")
public class BaseController {
    @GetMapping
    public ResultData getResult(){
        return ResultData.success("成功");
    }
}
