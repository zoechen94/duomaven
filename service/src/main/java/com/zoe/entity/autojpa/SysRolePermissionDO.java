package com.zoe.entity.autojpa;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
@Data
@Table(name = "sys_role_permission")
@Entity
public class SysRolePermissionDO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;
    Long roleId;
    Long permissionId;
}
