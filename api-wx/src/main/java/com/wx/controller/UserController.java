package com.wx.controller;

import com.zoe.spring.resultInfo.ResultData;
import com.zoe.spring.utils.DateUtil;
import com.zoe.spring.utils.HttpUtils;
import com.zoe.spring.utils.PassWordUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenYaLan on 2018/7/9
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Value("${base.url}")
    private String url;

    @Value("${account.sid}")
    private String account;

    @Value("${auth.token}")
    private String token;

    @Value("${resp.data.type}")
    private String type;

    @Value("${sms.content}")
    private String content;

    @ApiOperation(value = "短信验证码")
    @PostMapping
    @ApiImplicitParam(name = "iphone",value = "手机号",defaultValue = "17374707239",paramType = "query",dataType = "String")
    public ResultData sendSms(String iphone) throws IOException {
        Map map=new HashMap<>();
        map.put("accountSid",account);
        int random= (int) ((Math.random()*9+1)*100000);
        content= String.format(content,random,5);
        map.put("smsContent", content);
        String timeStamp=DateUtil.date2StringNoKong(new Date());
        map.put("timestamp",timeStamp);
        map.put("sig",PassWordUtils.md5(account,token,timeStamp));
        map.put("to",iphone);
        map.put("respDataType",type);
        Object abc=HttpUtils.post(url, (Map<String, String>) map);
        return ResultData.success(abc);
    }
}
