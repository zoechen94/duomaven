package com.zoe.service;

import com.zoe.entity.SysRolePermission;

import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
public interface SysRolePermissionService {
    int insertBatch(List<SysRolePermission> list);
}
