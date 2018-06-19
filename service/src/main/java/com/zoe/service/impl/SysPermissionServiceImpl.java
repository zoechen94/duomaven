package com.zoe.service.impl;

import com.zoe.mapper.SysPermissionMapper;
import com.zoe.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Permission;
import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Override
    public int insertBatch(List<Permission> permissionList) {
        return sysPermissionMapper.insertBatch(permissionList);
    }
}
