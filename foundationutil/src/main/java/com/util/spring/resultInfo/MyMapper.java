package com.util.spring.resultInfo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by ChenYaLan on 2018/7/18
 **/
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {
}
