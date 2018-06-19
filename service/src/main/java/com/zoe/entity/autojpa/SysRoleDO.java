package com.zoe.entity.autojpa;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
@Data
@Entity
@Table(name = "sys_role")
public class SysRoleDO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;
    String roleName;
    int level;
}
