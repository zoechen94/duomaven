package com.zoe.entity;
import java.io.Serializable;

public class SysPermission implements Serializable {
    private static final long serialVersionUID = -2726325902196359308L;
    private Long id;

    private String permissionCn;

    private String permissionEn;

    private String url;

    public SysPermission(String permissionCn,String permissionEn,String url){
        this.permissionCn=permissionCn;
        this.permissionEn=permissionEn;
        this.url=url;
    }
    public SysPermission(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionCn() {
        return permissionCn;
    }

    public void setPermissionCn(String permissionCn) {
        this.permissionCn = permissionCn == null ? null : permissionCn.trim();
    }

    public String getPermissionEn() {
        return permissionEn;
    }

    public void setPermissionEn(String permissionEn) {
        this.permissionEn = permissionEn == null ? null : permissionEn.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}