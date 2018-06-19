package com.zoe.entity;

import com.zoe.service.SysUserService;

public class SysRole {
    private Long id;

    private Integer level;

    private String roleName;

    public SysRole(Integer level,String roleName){
        this.level=level;
        this.roleName=roleName;
    }
    public SysRole(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }
}