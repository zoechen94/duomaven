package com.zoe.controller;

import com.zoe.spring.resultInfo.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springfox.documentation.annotations.ApiIgnore;


/**
 * Created by 陈亚兰 on 2018/6/20.
 */
@RestController
@RequestMapping("/user")
@Api(description = "用户操作")
public class UserController {
    private static Logger logger= LoggerFactory.getLogger(UserController.class);
    @ApiOperation("登陆")
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account",value = "账号",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "password",value = "密码",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "remember",value = "是否记住我",paramType = "query",dataType = "Boolean")
    })
    public ResultData login(String account,String password,Boolean remember,@ApiIgnore RedirectAttributes redirectAttributes){
        UsernamePasswordToken token = new UsernamePasswordToken(account,password,remember);
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + account + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + account + "]进行登录验证..验证通过");
        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + account + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("message", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + account + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "密码不正确");
        } catch (LockedAccountException lae) {
            logger.info("对用户[" + account + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + account + "]进行登录验证..验证未通过,错误次数过多");
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + account + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }
        if(currentUser.isAuthenticated()){
            return ResultData.success("登陆成功");
        }else{
            token.clear();
            return ResultData.error("登陆失败");
        }
    }

    @ApiOperation("退出")
    @DeleteMapping("/logout")
    public ResultData loginOut(RedirectAttributes redirectAttributes){
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message","您已安全退出");
        return ResultData.success("用户退出");
    }
}
