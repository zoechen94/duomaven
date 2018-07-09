package com.wx.controller;

import com.google.gson.reflect.TypeToken;
import com.wx.entity.WXJSON;
import com.zoe.spring.resultInfo.ResultData;
import com.zoe.spring.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ChenYaLan on 2018/7/9
 * 登陆
 **/
@RequestMapping("/login")
@RestController
public class LoginController {
    @Value("${wx.appid}")
    private String appID;

    @Value("${wx.secret}")
    private String secret;

    @GetMapping
    public ResultData login(String code) throws Exception {
         String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appID+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
         WXJSON wxjson=HttpUtils.get(url,new TypeToken<WXJSON>(){});
         return ResultData.success(wxjson);
    }
}
