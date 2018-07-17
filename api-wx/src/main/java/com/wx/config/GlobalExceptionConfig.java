package com.wx.config;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ChenYaLan on 2018/7/16
 **/
@ControllerAdvice
public class GlobalExceptionConfig {
    @ResponseBody
    @ExceptionHandler(value = UnauthorizedException.class)
    public ModelAndView unauthorizedException(){
          return new ModelAndView("unAuth");
    }
}
