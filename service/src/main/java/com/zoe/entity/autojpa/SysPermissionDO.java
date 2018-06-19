package com.zoe.entity.autojpa;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_permission")
public class SysPermissionDO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String permissionCn;

    private String permissionEn;

    private String url;

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