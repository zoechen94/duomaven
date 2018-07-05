package com.zoe.controller;

import com.zoe.entity.User;
import com.zoe.spring.resultInfo.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/redis")
@RestController
public class RedisController {
    Logger log=LoggerFactory.getLogger(this.getClass());
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @GetMapping
    public ResultData setRedis(){
        Set set =new HashSet<>();
        set.add("kd11");
        set.add("kd=g");
        User user=new User();
        user.setAccount("账户1");
        user.setPassword("密码222");
        redisTemplate.opsForSet().add("name",set);
        redisTemplate.opsForSet().add(set,user);

        return ResultData.success("df");
    }
}
