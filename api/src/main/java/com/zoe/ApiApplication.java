package com.zoe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by 陈亚兰 on 2018/6/12.
 */
@SpringBootApplication
@MapperScan(basePackages ="com.zoe.mapper")
@ComponentScan(basePackages = {"com.zoe.*"})
public class ApiApplication {
    public static void main(String[] args){
        SpringApplication.run(ApiApplication.class,args);
    }
}
