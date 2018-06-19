package com.zoe.service.impl;

import com.zoe.entity.SysRolePermission;
import com.zoe.mapper.SysRolePermissionMapper;
import com.zoe.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Override
    public int insertBatch(List<SysRolePermission> list) {
        return sysRolePermissionMapper.insertBatch(list);
    }
}
