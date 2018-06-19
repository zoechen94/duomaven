package com.zoe.service;

import com.zoe.entity.User;
import com.zoe.entity.vo.UserVO;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
public interface SysUserService {
    UserVO selectByAccount(String account);
}
