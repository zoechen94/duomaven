package com.util.spring.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Lan.Chen on 2018/10/25
 * 解析数组json
 */
public class JSonUtils {
    // 数据字段初始化
    private static void init(String jsonStr, List<String> titleStringList,
                             List<String> fieldStringList) {
        JSONArray jsonArray = JSONArray.parseArray(jsonStr);
        Iterator iterator = jsonArray.iterator();
        // 遍历出初始化数据
        while (iterator.hasNext()) {
            JSONObject object = (JSONObject) iterator.next();
            // 取出列头
            titleStringList.add(object.get("value") + "");
            // 取出字段
            fieldStringList.add(object.get("key") + "");
            System.out.println("value:"+object.get("value")+"   key:"+object.get("key"));
        }
    }
    public static void main(String[] args){
        String json="[{\"key\":\"key1\",\"value\":\"value1\"}{\"key\":\"key2\",\"value\":\"value2\"}]";
        List<String> title=new ArrayList<>();
        List<String> field=new ArrayList<>();
        init(json,title,field);
        System.out.println(title.toString());
        System.out.println(field.toString());
    }
}
