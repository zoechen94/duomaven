package com.util.spring.utils;

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

    /**
     * 所有形参的加密
     * @param strings
     * @return
     */
    public static String md5(String ... strings){
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]);
        }
        return DigestUtils.md5Hex(sb.toString());
    }
}
