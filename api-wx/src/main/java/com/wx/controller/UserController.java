package com.wx.controller;

import com.util.spring.resultInfo.ResultData;
import com.util.spring.utils.DateUtil;
import com.util.spring.utils.HttpUtils;
import com.util.spring.utils.PassWordUtils;
import com.util.spring.utils.VerifyCodeUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Value("${sms.timeout}")
    private int timeout;

    @Value("${sms.content}")
    private String content;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @ApiOperation(value = "短信验证码")
    @PostMapping
    @ApiImplicitParam(name = "iphone",value = "手机号",defaultValue = "17374707239",paramType = "query",dataType = "String")
    public ResultData sendSms(String iphone) throws IOException {
        Map map=new HashMap<>();
        map.put("accountSid",account);
        int random= (int) ((Math.random()*9+1)*100000);
        content= String.format(content,random,5);
        redisTemplate.opsForValue().set(iphone,random+"",timeout,TimeUnit.SECONDS);
        map.put("smsContent", content);
        String timeStamp=DateUtil.date2StringNoKong(new Date());
        map.put("timestamp",timeStamp);
        map.put("sig",PassWordUtils.md5(account,token,timeStamp));
        map.put("to",iphone);
        map.put("respDataType",type);
        Object abc=HttpUtils.post(url, (Map<String, String>) map);
        return ResultData.success(abc);
    }

    @GetMapping
    @ApiImplicitParam(name = "iphone",value = "手机号",defaultValue = "17374707239",dataType = "String",paramType = "query")
    public ResultData get(String iphone){
        System.out.println(redisTemplate.opsForValue().get(iphone));
        if(redisTemplate.opsForValue().get(iphone)==null){
            return ResultData.success("已过期");
        }
        return ResultData.success(redisTemplate.opsForValue().get(iphone));
    }

    @GetMapping("/verifyCode")
    public void generateVerification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        response.setContentType("image/jpeg");
        //生成随机字符串
        String verifyCode=VerifyCodeUtils.generateVerifyCode(4);
        redisTemplate.opsForValue().set("verifyCode",verifyCode);
        int w=100,h=30;
        VerifyCodeUtils.outputImage(w,h,response.getOutputStream(),verifyCode);
    }
}
