package com.wx.config;

import com.util.spring.resultInfo.ResultData;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ChenYaLan on 2018/7/16
 **/
@ControllerAdvice
public class GlobalExceptionConfig {
    @ResponseBody
    @ExceptionHandler(value = UnauthorizedException.class)
    public ResultData unauthorizedException(HttpServletRequest request, UnauthorizedException e){
        ResultData resultData=new ResultData();
        resultData.setMessage(e.getMessage());
        resultData.setResult("用户权限不够");
        resultData.setCode(401);
        resultData.setUrl(request.getRequestURI());
        return resultData;
    }
}
