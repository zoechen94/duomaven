package com.util.spring.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.util.spring.test.ObjectInfo;
import com.util.spring.test.PatentInfo;

import java.util.List;

/**
 * Created by 陈亚兰 on 2018/6/13.
 */
public class Object2Entity {
    public static<T> T exe(Object o, TypeToken<T> token){
        Gson gson=new Gson();
        T t=gson.fromJson(o.toString(),token.getType());
        return t;
    }
    public static void main(String[] args){
        Object o="{\n" +
                "  \"status\": \"SUCCESS\",\n" +
                "  \"result\": [\n" +
                "    {\n" +
                "      \"pageName\": \"专利基础信息\",\n" +
                "      \"empId\": 32470,\n" +
                "      \"orderIndex\": 1,\n" +
                "      \"columnEn\": \"pubnumer\",\n" +
                "      \"columnCn\": \"公开号\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"pageName\": \"专利基础信息\",\n" +
                "      \"empId\": 32470,\n" +
                "      \"orderIndex\": 2,\n" +
                "      \"columnEn\": \"title\",\n" +
                "      \"columnCn\": \"发明名称\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"pageName\": \"专利基础信息\",\n" +
                "      \"empId\": 32470,\n" +
                "      \"orderIndex\": 3,\n" +
                "      \"columnEn\": \"appdate\",\n" +
                "      \"columnCn\": \"申请日\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"pageName\": \"专利基础信息\",\n" +
                "      \"empId\": 32470,\n" +
                "      \"orderIndex\": 4,\n" +
                "      \"columnEn\": \"applicationname\",\n" +
                "      \"columnCn\": \"申请（专利权）人\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"pageName\": \"专利基础信息\",\n" +
                "      \"empId\": 32470,\n" +
                "      \"orderIndex\": 5,\n" +
                "      \"columnEn\": \"lprs\",\n" +
                "      \"columnCn\": \"最新法律状态\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"info\": \"调用成功\"\n" +
                "}";
        //objectInfo是自定义类
        ObjectInfo objectInfo=Object2Entity.exe(o,new TypeToken<ObjectInfo>(){});
        System.out.println("info:"+objectInfo.getInfo()+"\t\t"+"status:"+objectInfo.getStatus());
        List<PatentInfo> patentInfoList=objectInfo.getResult();
        patentInfoList.forEach(n->
            System.out.println("pageName:"+n.getPageName()+"\t\t"+"columnCN:"+n.getColumnCn())
        );
    }
}
