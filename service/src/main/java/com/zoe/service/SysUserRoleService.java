package com.zoe.service;

import com.zoe.entity.SysUserRole;

import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
public interface SysUserRoleService {
    int insertBatch(List<SysUserRole> list);
}
