package com.zoe.spring.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMddHHmmss");
    public static Date string2Date(String strDate) throws ParseException {
        return sdf.parse(strDate);
    }

    public static String date2String(Date date){
        return sdf.format(date);
    }

    public static String date2StringNoKong(Date date){
        return sdf2.format(date);
    }

}
