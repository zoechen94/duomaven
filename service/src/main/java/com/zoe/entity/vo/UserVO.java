package com.zoe.entity.vo;

import com.zoe.entity.SysPermission;
import com.zoe.entity.SysRole;
import lombok.Data;

import java.util.Set;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
@Data
public class UserVO {
    private Long userId;
    private String account;
    private String username;
    private String salt;
    private Set<SysPermission> sysPermissions;
    private Set<SysRole> sysRoles;
}
