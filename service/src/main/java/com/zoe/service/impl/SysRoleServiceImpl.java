package com.zoe.service.impl;

import com.zoe.entity.SysRole;
import com.zoe.mapper.SysRoleMapper;
import com.zoe.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public int insertBatch(List<SysRole> sysRoleList) {
        return sysRoleMapper.insertBatch(sysRoleList);
    }
}
