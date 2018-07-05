package com.zoe.service.redis;

import com.zoe.dao.UserTokenDao;
import com.zoe.entity.redis.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTokenServiceImpl implements UserTokenService {
    @Autowired
    private UserTokenDao userTokenDao;
    @Override
    public UserToken add(UserToken userToken) {
        return userTokenDao.save(userToken);
    }

    @Override
    public UserToken find(String account) {
        return userTokenDao.findOne(account);
    }

    @Override
    public void delete(String username) {
        userTokenDao.delete(username);
    }
}
