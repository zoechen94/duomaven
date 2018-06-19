package com.zoe.entity;

public class SysRolePermission {
    private Long id;

    private Long permissionId;

    private Long roleId;

    public SysRolePermission(Long roleId,Long permissionId){
        this.permissionId=permissionId;
        this.roleId=roleId;
    }

    public SysRolePermission(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}