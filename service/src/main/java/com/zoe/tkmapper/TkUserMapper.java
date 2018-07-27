package com.zoe.tkmapper;

import com.util.spring.resultInfo.MyMapper;
import com.zoe.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by ChenYaLan on 2018/7/18
 **/
@Mapper
public interface TkUserMapper extends MyMapper<User> {
    @Select("select * from user where username=#{username}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "account",column = "account"),
            @Result(property = "salt", column = "salt")
    })
    User selectByName(@Param("username") String username);

    @Select("select count(1) from user")
    int selectNumber();

    @Select("select * from user")
    List<User> getAll();
}
