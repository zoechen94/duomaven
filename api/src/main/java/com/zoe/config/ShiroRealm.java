package com.zoe.config;

import com.zoe.entity.SysPermission;
import com.zoe.entity.SysRole;
import com.zoe.entity.vo.UserVO;
import com.zoe.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
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

    /**
     * 授权
     * 用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("获取授权用户:"+principalCollection.toString());
        UserVO user = sysUserService.selectByAccount((String) principalCollection.getPrimaryPrincipal());


        //把principals放session中 key=userId value=principals
        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getUserId()),SecurityUtils.getSubject().getPrincipals());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        /**
         * 就是说如果在shiro配置文件中添加了filterChainDefinitionMap.put(“/add”, “perms[权限添加]”);
         * 就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问，
         * 如果在shiro配置文件中添加了filterChainDefinitionMap.put(“/add”, “roles[100002]，perms[权限添加]”);
         * 就说明访问/add这个链接必须要有“权限添加”这个权限和具有“100002”这个角色才可以访问。
         */
        //赋予角色 1,超级管理员，2，管理员，3，普通用户
        for(SysRole userRole:user.getSysRoles()){
            info.addRole(userRole.getRoleName());
        }
        //赋予权限
        for(SysPermission permission:user.getSysPermissions()){
            info.addStringPermission(permission.getPermissionEn());
        }
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("登陆认证 +"  + authenticationToken.toString());

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName=token.getUsername();
        logger.info("认证用户:"+userName+"\t密码:"+token.getPassword());

        UserVO user = sysUserService.selectByAccount(token.getUsername());
        if (user != null) {
//            Session session = SecurityUtils.getSubject().getSession();
//            session.setAttribute("user", user);
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            return new SimpleAuthenticationInfo(userName,token.getPassword(),getName());
        } else {
            return null;
        }
    }
}
