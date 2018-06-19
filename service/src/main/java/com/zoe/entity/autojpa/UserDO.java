package com.zoe.entity.autojpa;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by 陈亚兰 on 2018/6/19.
 */
@Data
@Table(name = "user")
@Entity
public class UserDO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private String password;
    private String account;
}
