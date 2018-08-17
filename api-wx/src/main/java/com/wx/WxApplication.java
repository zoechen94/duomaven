package com.wx;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Properties;

/**
 * Created by ChenYaLan on 2018/7/9
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.wx.*","com.zoe.*"})
@MapperScan(basePackages ={"com.zoe.mapper","com.zoe.tkmapper","com.zoe.dao"})
public class WxApplication {
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("returnPageInfo", "check");
        p.setProperty("params", "count=countSql");
        pageHelper.setProperties(p);
        return pageHelper;
    }

    public static void main(String[] args){
        SpringApplication.run(WxApplication.class);
    }
}
