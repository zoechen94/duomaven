package com.wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.util.spring.resultInfo.ResultData;
import com.util.spring.utils.DateUtil;
import com.util.spring.utils.HttpUtils;
import com.util.spring.utils.PassWordUtils;
import com.util.spring.utils.VerifyCodeUtils;
import com.zoe.entity.User;
import com.zoe.service.SysUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @Autowired
    private SysUserService userService;

    @ApiOperation(value = "短信验证码")
    @PostMapping
    @ApiImplicitParam(name = "iphone",value = "手机号",defaultValue = "17374707239",paramType = "query",dataType = "String")
    public ResultData sendSms(String iphone) throws IOException {
        Map map=new HashMap<>();
        map.put("accountSid",account);
        int random= (int) ((Math.random()*9+1)*100000);
        content= String.format(content,random,5);
        redisTemplate.opsForValue().set(iphone,random+"",timeout,TimeUnit.SECONDS);//把验证码放在redis里存储
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

    @ApiOperation(value = "根据用户名查询用户--真查")
    @GetMapping("/getByUserName")
    @ApiImplicitParam(name = "username",value = "用户名",defaultValue = "路人",dataType = "String",paramType = "query")
    public Object getUser(String username){
        User user=userService.selectTkMapper(username);
        JSONObject json=new JSONObject();
        json.put("code",200);
        json.put("result",user);
        json.put("message","success");
        json.put("num",userService.selectNum());
        return json;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页面",defaultValue = "1",dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "size",value = "个数",defaultValue = "10",dataType = "Integer",paramType = "query")
    })
    @GetMapping("/getAllTk")
    @ApiOperation(value = "得到所有用户Tk")
    public ResultData getAllUserTk(int page,int size){
        return ResultData.success(userService.getAllTk(page,size));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页面",defaultValue = "1",dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "size",value = "个数",defaultValue = "10",dataType = "Integer",paramType = "query")
    })
    @GetMapping("/getAll")
    @ApiOperation(value = "得到所有用户Tk")
    public ResultData getAllUser(int page,int size){
        System.out.println("tk:---page:"+page+"\tsize:"+size);
        return ResultData.success(userService.getAll(page,size));
    }
}
