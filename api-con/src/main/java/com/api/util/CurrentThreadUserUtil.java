package com.api.util;

import com.zoe.entity.vo.UserVO;

/**
 * Created by Lan.Chen on 2018/10/26
 */
public class CurrentThreadUserUtil {
    private static ThreadLocal<UserVO> current=new ThreadLocal<>();

    /**
     * 添加用户到本地变量
     * @param userVO
     */
    public static void add(UserVO userVO){
        current.set(userVO);
    }

    /**
     * 得到变量
     * @return
     */
    public static UserVO getUser(){
       return current.get();
    }
}
