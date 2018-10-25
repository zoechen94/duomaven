package com.api.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Lan.Chen on 2018/10/25
 * 拦截器
 * token存，ehcache,redis,db都可以
 */
@Component
public class AccessTokenInterceptor extends HandlerInterceptorAdapter {
    private Logger logger= LoggerFactory.getLogger(AccessTokenInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag=false;
        //token
        String accessToken= (String) request.getSession().getAttribute("token");
        if(!StringUtils.isEmpty(accessToken)){
            flag=true;
        }
        if(!flag){
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().append("accessToken Error");
        }
        return flag;
    }
}
