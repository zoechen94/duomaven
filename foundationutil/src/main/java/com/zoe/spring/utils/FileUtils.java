package com.zoe.spring.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/13.
 */
public class FileUtils {
    public static void putAllFilesInOneDirectory(String source,String target) throws IOException {
        List<File> listFiles=new ArrayList<>();
        //如果没有目录就新建一个目录
        if(!new File(target).exists()){
            new File(target).mkdir();
        }

        File file=new File(source);
        File[] files=file.listFiles();//listFiles会把这个目录下的所有罗列，包括子文件夹
        if(files==null){
            return;//如果没有就结束
        }
        for (File f:
             files) {
            if(f.isFile()){
                //通常遇到的需求可能只是把某种类型的放在一起，则可以通过后缀名过滤
//                if(f.getName().toLowerCase().endsWith(".xlsx"))
                listFiles.add(f);
            }else if(f.isDirectory()){
                putAllFilesInOneDirectory(f.getAbsolutePath(),target);
            }
        }
        for(File f:listFiles){
            copyFile(source+f.getName(),target+f.getName());
        }
    }

    /**
     * 复制文件
     * @param source
     * @param target
     * @throws IOException
     */
    public static void copyFile(String source,String target) throws IOException {
        File oldFile=new File(source);
        File file=new File(target);
        FileInputStream in=new FileInputStream(oldFile);
        FileOutputStream out=new FileOutputStream(file);
        byte[] bytes=new byte[2097152];
        while ((in.read(bytes))!=-1){
            out.write(bytes);
        }
    }
    public static void main(String[] args) throws IOException {
        FileUtils.putAllFilesInOneDirectory("D:\\catdemo\\","D:\\che\\");
    }
}
