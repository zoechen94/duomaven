package com.zoe.dao;

import com.zoe.entity.redis.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenDao extends JpaRepository<UserToken,String> {
}
