package com.wx.controller;

import com.util.spring.resultInfo.ResultData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ChenYaLan on 2018/7/16
 **/
@RequestMapping
@RestController
public class ExceptionController {
    @GetMapping("/unAuth")
    public ResultData unAuth(){
        ResultData resultData=new ResultData();
        resultData.setCode(401);
        resultData.setResult("用户未拥有此权限");
        return resultData;
    }
}
