package com.wx.controller;

import com.util.spring.resultInfo.ResultData;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ChenYaLan on 2018/7/16
 **/
@RequestMapping
@RestController
public class ExceptionController {
    @RequestMapping("/unAuth")
    public ResultData unAuth(){
        throw new UnauthorizedException();
    }
}
