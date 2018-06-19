package com.zoe.entity.autojpa;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
@Data
@Entity
@Table(name = "sys_user_role")
public class SysUserRoleDO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long userId;
    private Long roleId;
}
