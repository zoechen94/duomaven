package com.zoe.spring.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 陈亚兰 on 2018/6/13.
 */
public class ExcelUtils {
    private static final String excel2003=".xls";
    private static final String excel2007=".xlsx";


    /**根据后缀名生成WorkBook**/
    public static Workbook getWorkBook(InputStream in, String name) throws IOException {
        String fileType=name.substring(name.lastIndexOf("."));//.xls Or .xlsx
        Workbook wb=null;
        if(fileType.endsWith(excel2003)){
            wb=new HSSFWorkbook(in);
        }else if(fileType.endsWith(excel2007)){
            wb=new XSSFWorkbook(in);
        }
        return wb;
    }
}
