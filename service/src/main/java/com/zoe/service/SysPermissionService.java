package com.zoe.service;

import com.zoe.entity.SysPermission;

import java.security.Permission;
import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
public interface SysPermissionService {
    int insertBatch(List<Permission> permissionList);
    List<SysPermission> selectAll();
    SysPermission findOne(Long id);
}
