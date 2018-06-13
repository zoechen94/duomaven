package com.zoe.spring.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 陈亚兰 on 2018/5/7.
 */
public class ReplaceXmlCharacterUtils {
    /**
     * example
     * @param str
     * @param dbName
     * @param pubDate
     * @param appNumber
     * @param url
     * @return
     */
    public static String getHtmlContent(String str,String dbName,String pubDate,String appNumber,String url){
        if(str==null||str.equals(""))return "";
        //正则匹配<tables
        String regTables="<tables([\\s\\S]*?)>";
        str=regStringToDiy(str,regTables,"");

        String reg="\\\"";
        str=regStringToDiy(str,reg,"'");
        return str;
    }

    /**
     *
     * @param source 原字符串
     * @param reg    正则表达式
     * @param toString  返回字符串
     * @return
     */
    private static String regStringToDiy(String source,String reg,String toString){
        Matcher matcher=Pattern.compile(reg).matcher(source);
        List<String> beMath=new ArrayList<>();
        while(matcher.find()){
            String temp=source.substring(matcher.start(),matcher.end());
            beMath.add(temp);
        }
        for(int i=0;i<beMath.size();i++){
            source=source.replace(beMath.get(i),toString);
        }
        return source;
    }
}

