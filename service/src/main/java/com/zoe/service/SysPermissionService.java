package com.zoe.service;

import java.security.Permission;
import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
public interface SysPermissionService {
    int insertBatch(List<Permission> permissionList);
}
