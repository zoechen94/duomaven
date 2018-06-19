package com.zoe.service;

import com.zoe.entity.SysRole;

import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
public interface SysRoleService {
    int insertBatch(List<SysRole> sysRoleList);
}
