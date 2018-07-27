package com.zoe.controller;

import com.alibaba.fastjson.JSONObject;
import com.util.spring.utils.*;
import com.zoe.entity.User;
import com.zoe.entity.redis.UserToken;
import com.zoe.entity.vo.UserVO;
import com.zoe.service.SysUserService;
import com.zoe.service.redis.UserTokenService;
import com.util.spring.resultInfo.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Created by 陈亚兰 on 2018/6/20.
 */
@RestController
@RequestMapping("/user")
@Api(description = "用户操作")
@PropertySource(value = "classpath:db.properties")
public class UserController {
    private static Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private SysUserService sysUserService;
    @Value("${spring.redis.timeout}")
    private int redisTimeout;

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
    @ApiOperation("登陆")
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account",value = "账号",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "password",value = "密码",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "remember",value = "是否记住我",defaultValue = "false",paramType = "query",dataType = "Boolean")
    })
    public ResultData login(String account, String password, Boolean remember, @ApiIgnore RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response){
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
            UserVO userVO=sysUserService.selectByAccount(account);
            String salt=userVO.getSalt();
            String tokenLing=PassWordUtils.md5PassWord(password,salt);
            response.addCookie(CookieUtils.getCookie("account",account,redisTimeout,"/",false));
            response.addCookie(CookieUtils.getCookie("token",tokenLing,redisTimeout,"/",false));

            Set<String> sysPermissions=new HashSet<>();
            userVO.getSysPermissions().forEach(n->{
                sysPermissions.add(n.getPermissionEn());
            });
            userTokenService.add(new UserToken(account,tokenLing,sysPermissions, DateUtils.addSeconds(new Date(),timeout)));
            return ResultData.loginSuccess("登陆成功",request.getSession().getId());
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
