package com.zoe.service.redis;

import com.zoe.entity.redis.UserToken;

public interface UserTokenService {
    UserToken add(UserToken userToken);
    UserToken find(String account);
    void delete(String username);
}
