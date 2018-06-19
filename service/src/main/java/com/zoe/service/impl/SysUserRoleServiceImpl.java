package com.zoe.service.impl;

import com.zoe.entity.SysUserRole;
import com.zoe.mapper.SysUserRoleMapper;
import com.zoe.service.SysUserRoleService;
import com.zoe.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService{
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public int insertBatch(List<SysUserRole> list) {
        return sysUserRoleMapper.insertBatch(list);
    }
}
