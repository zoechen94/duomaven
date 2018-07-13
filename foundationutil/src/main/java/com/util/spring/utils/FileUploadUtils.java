package com.util.spring.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * Created by 陈亚兰 on 2018/6/12.
 * 文件上传工具类
 */
@Component
public class FileUploadUtils {
    @Value("${upload.path}")
    private String valueUpload;
    private static String uploadPath;

    @PostConstruct
    public void init() {
        uploadPath=valueUpload;
    }
    /**
     * 根据路径名创建目录，没有目录则创建目录
     * @param path
     */
    public static void createDir(String path){
        File fileDir = new File(path);
        if(!fileDir.exists()&&!fileDir.isDirectory()){//判断目录 是否存在
            fileDir.mkdir();
        }
    }

    public static String uploadFile(MultipartFile file) throws IOException {
             createDir(uploadPath);
             String path=uploadPath+file.getOriginalFilename();
             FileCopyUtils.copy(file.getBytes(),new File(path));
             return file.getOriginalFilename();
    }
}
