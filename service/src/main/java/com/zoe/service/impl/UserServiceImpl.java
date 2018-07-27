package com.zoe.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoe.entity.User;
import com.zoe.entity.vo.UserVO;
import com.zoe.mapper.UserMapper;
import com.zoe.service.SysUserService;
import com.zoe.tkmapper.TkUserMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
@Service
public class UserServiceImpl implements SysUserService {
    private static final org.slf4j.Logger log=LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TkUserMapper tkUserMapper;

    @Override
    public UserVO selectByAccount(String account) {
        return userMapper.selectByAccount(account);
    }



    @Override
    public PageInfo<User> getAll(int page,int size) {
        PageHelper.startPage(page,size);
        log.info("----------xml-getAll-------page:"+page+"\tsize:"+size);
        List<User> list=userMapper.selectAll();
        PageInfo<User> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public User selectTkMapper(String username) {
        return tkUserMapper.selectByName(username);
    }

    @Override
    public int selectNum() {
        return tkUserMapper.selectNumber();
    }

    @Override
    public PageInfo<User> getAllTk(int page, int size) {
        PageHelper.startPage(page,size);
        log.info("----------xml-getAll-------page:"+page+"\tsize:"+size);
        List<User> list=tkUserMapper.getAll();
        PageInfo<User> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
}
