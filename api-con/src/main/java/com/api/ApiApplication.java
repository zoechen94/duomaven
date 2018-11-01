package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Created by Lan.Chen on 2018/10/25
 * 启动类
 */
@Configuration
@SpringBootApplication
public class ApiApplication {
    public static void main(String[] args){
        SpringApplication.run(ApiApplication.class,args);
    }

    /**
     * 文件上传大小限制，maxFileSize——单个文件最大大小值   maxRequestSize——上传总文件大小
     * springboot中有两种方式，1 在application.properties里配置两行代码
     * spring.http.multipart.maxFileSize=10MB
     * spring.http.multipart.maxRequestSize=10MB
     * 2 用bean的方式，一定要在类上加@Configuration ，一般写在启动类里就好
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory=new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("50MB");//KB,MB
        //设置总上传数据总大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }
}
