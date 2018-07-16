package com.wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.util.spring.resultInfo.ResultData;
import com.util.spring.utils.HttpUtils;
import com.wx.entity.WXJSON;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by ChenYaLan on 2018/7/9
 * 登陆
 **/
@RestController
public class LoginController {

    private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
    @Value("${wx.appid}")
    private String appID;

    @Value("${wx.secret}")
    private String secret;

    @GetMapping("/login")
    @ApiImplicitParam(name = "code",value = "code",dataType = "String",paramType = "query")
    public ResultData login(String code) throws Exception {
         String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appID+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
         WXJSON wxjson=HttpUtils.get(url,new TypeToken<WXJSON>(){});
         return ResultData.success(wxjson);
    }

    @GetMapping("/test")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName",value = "账户",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "String",paramType = "query")
    })
    public ResultData loginTest(String loginName,String password){
        try {
            // 创建shiro需要的token
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginName, password.toCharArray());
            Subject current=SecurityUtils.getSubject();
//            usernamePasswordToken.setRememberMe(true);// 记住
            try {
                logger.info("开始验证["+loginName+"]");
                current.login(usernamePasswordToken);
                logger.info("验证结束["+loginName+"]");
            } catch (UnknownAccountException uae) {
                logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,未知账户");
                return ResultData.error("对用户[" + loginName + "]进行登录验证..验证未通过,未知账户");
            } catch (IncorrectCredentialsException ice) {
                logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,错误的凭证");
                ice.printStackTrace();
                return ResultData.error("对用户[" + loginName + "]进行登录验证..验证未通过,错误的凭证");
            } catch (LockedAccountException lae) {
                logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,账户已锁定");
                return ResultData.error("对用户[" + loginName + "]进行登录验证..验证未通过,账户已锁定");
            } catch (ExcessiveAttemptsException eae) {
                logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,错误次数过多");
                return ResultData.error("对用户[" + loginName + "]进行登录验证..验证未通过,错误次数过多");
            } catch (AuthenticationException ae) {
                // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
                logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,堆栈轨迹如下");
                ae.printStackTrace();
                return ResultData.error("用户名或密码不正确");
            }
            if(current.isAuthenticated()){
                return ResultData.success("Login Success!");
            }
            return null;
        } catch (Exception e) {
            return ResultData.error("登陆时候发生异常," + e.getMessage());
        }
    }

    @GetMapping("/admin/hello.do")
    public String helloAdmin() {
        return "Hello Admin, From Server";
    }

    @GetMapping("/welcome.do")
    public String loginSuccess() {
        return "welcome";
    }

    @GetMapping("/403.do")
    public Object error403(HttpServletResponse response) {
        response.setStatus(403);
        JSONObject object = new JSONObject();
        object.put("message", "用户权限不够");
        return object;
    }
}
