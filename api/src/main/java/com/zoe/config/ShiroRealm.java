package com.zoe.config;

import com.zoe.entity.SysPermission;
import com.zoe.entity.SysRole;
import com.zoe.entity.User;
import com.zoe.entity.vo.UserVO;
import com.zoe.service.SysUserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService sysUserService;
    private Logger logger=LoggerFactory.getLogger(this.getClass());
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("doGetAuthorizationInfo+"+principalCollection.toString());
        UserVO user = sysUserService.selectByAccount((String) principalCollection.getPrimaryPrincipal());


        //把principals放session中 key=userId value=principals
        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getUserId()),SecurityUtils.getSubject().getPrincipals());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //赋予角色
        for(SysRole userRole:user.getSysRoles()){
            info.addRole(userRole.getRoleName());
        }
        //赋予权限
        for(SysPermission permission:user.getSysPermissions()){
//            if(StringUtils.isNotBlank(permission.getPermCode()))
            info.addStringPermission(permission.getPermissionEn());
        }

        //设置登录次数、时间
//        userService.updateUserLogin(user);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("doGetAuthenticationInfo +"  + authenticationToken.toString());

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName=token.getUsername();
        logger.info(userName+token.getPassword());

        UserVO user = sysUserService.selectByAccount(token.getUsername());
        if (user != null) {
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user", user);
            return new SimpleAuthenticationInfo(userName,"xxx",getName());
        } else {
            return null;
        }
    }
}
