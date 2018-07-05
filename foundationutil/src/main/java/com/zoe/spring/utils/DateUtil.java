package com.zoe.spring.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static Date string2Date(String strDate) throws ParseException {
        return sdf.parse(strDate);
    }

    public static String date2String(Date date){
        return sdf.format(date);
    }

}
