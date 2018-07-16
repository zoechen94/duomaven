package com.wx.exception;

/**
 * Created by ChenYaLan on 2018/7/16
 **/
public class UnAuthException extends RuntimeException{
    public UnAuthException(String info){
        super(info);
    }
}
