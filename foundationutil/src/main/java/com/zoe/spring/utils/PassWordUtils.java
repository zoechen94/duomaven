package com.zoe.spring.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by 陈亚兰 on 2018/6/13.
 * 用户密码
 */
public class PassWordUtils {

    public static String md5PassWord(String password,String salt){
        return DigestUtils.md5Hex(password+salt);
    }

    public static boolean equals(String password,String salt,String passDB){
        return md5PassWord(password,salt).equals(passDB);
    }

}
