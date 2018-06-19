package com.zoe.service.impl;

import com.zoe.entity.User;
import com.zoe.entity.vo.UserVO;
import com.zoe.mapper.UserMapper;
import com.zoe.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
@Service
public class UserServiceImpl implements SysUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserVO selectByAccount(String account) {
        return userMapper.selectByAccount(account);
    }
}
