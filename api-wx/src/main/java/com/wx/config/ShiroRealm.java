package com.wx.config;

import com.zoe.entity.SysPermission;
import com.zoe.entity.SysRole;
import com.zoe.entity.vo.UserVO;
import com.zoe.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by ChenYaLan on 2018/7/13
 **/
@Component
public class ShiroRealm extends AuthorizingRealm {
    private static final Logger log=LoggerFactory.getLogger(ShiroRealm.class);
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取用户授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("###########################执行Shiro权限认证############################");
        String loginName= (String) principals.fromRealm(getName()).iterator().next();
        if(StringUtils.isEmpty(loginName)){
            return null;
        }
        //查询登陆用户信息
        UserVO user=sysUserService.selectByAccount(loginName);
        if(user==null){
            log.warn("用户["+loginName+"]不存在");
            return null;
        }
        //创建一个授权对象
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        Set<SysPermission> permissions=user.getSysPermissions();
        if(permissions!=null&&!permissions.isEmpty()){
            permissions.forEach(n->{
                info.addStringPermission(n.getPermissionEn());
            });
        }
        Set<SysRole> sysRoles=user.getSysRoles();
        if(sysRoles!=null&&!sysRoles.isEmpty()){
            sysRoles.forEach(n->info.addRole(n.getRoleName()));
        }
        log.info("permissions:"+info.getStringPermissions()+"\troles:"+info.getRoles());

        //角色设置
        return info;
    }

    /**
     * 获取用户认证信息
     * @param
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("##############执行Shiro登陆认证##############");
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        //通过表单接收的用户名
        String loginName=token.getUsername();
        if(loginName!=null&&!loginName.equals("")){
            UserVO userVO=sysUserService.selectByAccount(loginName);
            if(userVO!=null){ //登陆的主要信息，可以是一个实体类的对象，但该实体类的对象一定是根据token的username查询得到的
               Object principal=token.getPrincipal();
               //创建shiro的用户认证对象
                //注意该对象的密码将会传递至后续步骤与前面登陆的Subject的密码进行比对
                SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(principal,token.getPassword(),getName());
                return authenticationInfo;
            }
        }
        return null;
    }
}
