package com.util.spring.resultInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by 陈亚兰 on 2018/6/12.
 * 封装返回json信息
 */
@Data
public class ResultData<T> implements Serializable {
    private static final long serialVersionUID = 484936467291213572L;
    public static final String SUCCESS_MESSAGE="success";
    public static final String ERROR_MESSAGE="failure";
    public static final int SUCCESS_CODE=200;
    public static final int ERROR_CODE=400;
    private String url;
    private String message;
    private int code;
    private T result;
    private String sessionId;

    public static <T> ResultData<T> success(T t){
        ResultData<T> resultData=new ResultData<T>();
        resultData.setMessage(SUCCESS_MESSAGE);
        resultData.setCode(SUCCESS_CODE);
        resultData.setResult(t);
        return resultData;
    }
    public static <T> ResultData<T> loginSuccess(T t,String sessionId){
        ResultData<T> resultData=new ResultData<T>();
        resultData.setMessage(SUCCESS_MESSAGE);
        resultData.setCode(SUCCESS_CODE);
        resultData.setResult(t);
        resultData.setSessionId(sessionId);
        return resultData;
    }
    public static <T> ResultData<T> error(T t){
        ResultData<T> resultData=new ResultData<T>();
        resultData.setResult(t);
        resultData.setCode(ERROR_CODE);
        resultData.setMessage(ERROR_MESSAGE);
        return resultData;
    }
}
