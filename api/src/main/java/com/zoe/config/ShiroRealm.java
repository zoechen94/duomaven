//package com.zoe.config;
//
//import com.zoe.entity.SysRole;
//import com.zoe.entity.User;
//import com.zoe.service.SysUserService;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * Created by 陈亚兰 on 2018/6/19.
// */
//public class ShiroRealm extends AuthorizingRealm {
//    @Autowired
//    private SysUserService sysUserService;
//    private Logger logger=LoggerFactory.getLogger(this.getClass());
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        logger.info("doGetAuthorizationInfo+"+principalCollection.toString());
//        User user = sysUserService.selectByAccount((String) principalCollection.getPrimaryPrincipal());
//
//
//        //把principals放session中 key=userId value=principals
//        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()),SecurityUtils.getSubject().getPrincipals());
//
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        //赋予角色
//        for(SysRole userRole:user.getRoles()){
//            info.addRole(userRole.getName());
//        }
//        //赋予权限
//        for(Permission permission:permissionService.getByUserId(user.getId())){
////            if(StringUtils.isNotBlank(permission.getPermCode()))
//            info.addStringPermission(permission.getName());
//        }
//
//        //设置登录次数、时间
////        userService.updateUserLogin(user);
//        return info;
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        logger.info("doGetAuthenticationInfo +"  + authenticationToken.toString());
//
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        String userName=token.getUsername();
//        logger.info(userName+token.getPassword());
//
//        User user = userService.getByUserName(token.getUsername());
//        if (user != null) {
////            byte[] salt = Encodes.decodeHex(user.getSalt());
////            ShiroUser shiroUser=new ShiroUser(user.getId(), user.getLoginName(), user.getName());
//            //设置用户session
//            Session session = SecurityUtils.getSubject().getSession();
//            session.setAttribute("user", user);
//            return new SimpleAuthenticationInfo(userName,user.getPassword(),getName());
//        } else {
//            return null;
//        }
////        return null;
//    }
//}
