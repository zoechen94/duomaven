package com.util.spring.utils;

import javax.servlet.http.Cookie;

public class CookieUtils {
    public static Cookie getCookie(String name, String value, int exprieTime, String path, Boolean security){
        Cookie cookie=new Cookie(name,value);
        cookie.setMaxAge(exprieTime);
        cookie.setPath(path);
        cookie.setSecure(security);
        return cookie;
    }
}
