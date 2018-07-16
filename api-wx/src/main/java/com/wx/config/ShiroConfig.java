package com.wx.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ChenYaLan on 2018/7/13
 * shiro权限管理配置
 **/
@Configuration
public class ShiroConfig {

    /**
     * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，
     * 内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法。
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aASA = new AuthorizationAttributeSourceAdvisor();
        aASA.setSecurityManager(defaultWebSecurityManager());
        return aASA;
    }
        /**
         * ehcache缓存方案<br/>
         * 简单的缓存,后续可更换为redis缓存,通过自己实现shiro的CacheManager接口和Cache接口
         *
         * @return
         */
    @Bean
    public EhCacheManager shiroEhCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

    /**
     * redis缓存方案
     *
     * @return
     */
    @Bean
    public RedisCacheManager shiroRedisCacheManager() {
        return new RedisCacheManager();
    }

    /****
     * 自定义Real
     *
     * @return
     */
    @Bean
    public ShiroRealm jdbcShiroRealm() {
        ShiroRealm realm = new ShiroRealm();
        // 根据情况使用缓存器
        realm.setCacheManager(shiroRedisCacheManager());//shiroEhCacheManager()
        return realm;
    }

    /***
     * 安全管理配置
     *
     * @return
     */

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();// 注意:！！！初始化成这个将会报错java.lang.IllegalArgumentException:
        // SessionContext must be an HTTP compatible
        // implementation.：模块化本地测试shiro的一些总结
        // 配置
        securityManager.setRealm(jdbcShiroRealm());
        // 注意这里必须配置securityManager
        SecurityUtils.setSecurityManager(securityManager);
        // 根据情况选择缓存器
        securityManager.setCacheManager(shiroRedisCacheManager());//shiroEhCacheManager()

        return securityManager;
    }

    /**
     * 配置shiro的拦截器链工厂,默认会拦截所有请求，并且不可配置
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        // 配置安全管理(必须)
        filterFactoryBean.setSecurityManager(defaultWebSecurityManager());

        /**#################################*/
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc", new AjaxPermissionsAuthorizationFilter());
        filterFactoryBean.setFilters(filterMap);
        /**#################################*/
        // 配置登陆的地址
        filterFactoryBean.setLoginUrl("/test");// 未登录时候跳转URL,如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        filterFactoryBean.setSuccessUrl("/welcome.do");// 成功后欢迎页面
        filterFactoryBean.setUnauthorizedUrl("/unAuth");// 未认证页面,未授权页面
        // 配置拦截地址和拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();// 必须使用LinkedHashMap,因为拦截有先后顺序
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问

//        filterChainDefinitionMap.put("/userNoLogin.do*", "anon");// 未登录跳转页面不设权限认证
        filterChainDefinitionMap.put("/test", "anon");// 登录接口不设置权限认真
//        filterChainDefinitionMap.put("/logout.do*", "anon");// 登出不需要认证
        filterChainDefinitionMap.put("/swagger-ui.html","anon");
        filterChainDefinitionMap.put("/swagger/**","anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**","anon");
        filterChainDefinitionMap.put("/v2/**","anon");

        // 以下配置同样可以通过注解
        // @RequiresPermissions("user:edit")来配置访问权限和角色注解@RequiresRoles(value={"ROLE_USER"})方式定义
        // 权限配置示例,这里的配置理论来自数据库查询
//        filterChainDefinitionMap.put("/user/**", "roles[ROLE_USER],perms[query]");// /user/下面的需要ROLE_USER角色或者query权限才能访问
        filterChainDefinitionMap.put("/user/**","perms[delete-user]");
//        filterChainDefinitionMap.put("/permission/**","roles[管理员]");
        // 剩下的其他资源地址全部需要用户认证后才能访问
        filterChainDefinitionMap.put("/**", "authc");
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return filterFactoryBean;
    }
}
