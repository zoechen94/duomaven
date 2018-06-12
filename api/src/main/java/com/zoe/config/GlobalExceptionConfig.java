package com.zoe.config;

import com.zoe.common.MyException;
import com.zoe.service.spring.resultInfo.ResultData;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 陈亚兰 on 2018/6/12.
 */
@ControllerAdvice
public class GlobalExceptionConfig {

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ResultData<String> getMyException(HttpServletRequest request, MyException e){
        ResultData resultData=new ResultData();
        resultData.setUrl(request.getRequestURI());
        resultData.setMessage("error");
        resultData.setCode(500);
        resultData.setResult(e.getMessage());
        return resultData;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultData<String> valueBindException(HttpServletRequest request,MethodArgumentNotValidException e){
        ResultData resultData=new ResultData();
        resultData.setUrl(request.getRequestURI());
        resultData.setResult(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        resultData.setCode(500);
        resultData.setMessage("error");
        return resultData;
    }
}
