package com.wx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ChenYaLan on 2018/7/9
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.*"})
@MapperScan(basePackages ="com.zoe.mapper")
public class WxApplication {
    public static void main(String[] args){
        SpringApplication.run(WxApplication.class);
    }
}
