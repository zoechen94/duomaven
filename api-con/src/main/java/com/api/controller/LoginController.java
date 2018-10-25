package com.api.controller;

import com.util.spring.resultInfo.ResultData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lan.Chen on 2018/10/25
 */
@RestController
@RequestMapping("/user")
public class LoginController {
    @GetMapping("/login")
    public ResultData login(HttpServletRequest request){
        request.getSession().setAttribute("token","123");
        return ResultData.success("登录过了");
    }
}
