package com.util.spring.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.util.spring.resultInfo.CompanyInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Lan.Chen on 2018/10/30
 * 使用代码时候，需要传入一个json字符串，格式是[{"key":"name","value":"姓名"}{"key":"id","value":"序号"}]  excel展示的字段顺序就是json中字段顺序
 * json中key是导出数组里的泛型类属性(填哪些属性导出哪些),value是中文，放在excel表头的，
 * 以及数据的List集合对象，还有一个sheet名--title字段
 * 其中每列自动适应宽度
 */
public class ExcelExportUtil<T> {

    private static List<String> keyList=new ArrayList<>();

    private static List<String> valueList=new ArrayList<>();

    public HSSFWorkbook setExcel(String title, List<T> tList,String json) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
         resolveJson(json);
        //        1.创建Excel工作薄对象
        HSSFWorkbook workbook=new HSSFWorkbook();
//        2.创建Excel工作表对象
        HSSFSheet sheet=workbook.createSheet(title);
        HSSFRow row=null;
//        3.创建Excel工作表的第一行，并填充列名
        row=sheet.createRow(0);
        for(int i=0;i<valueList.size();i++){
            row.createCell(i).setCellValue(valueList.get(i));
        }
        Field[] declaredFields = tList.get(0).getClass().getDeclaredFields();
//        4.将数据填充至表格中
        for(int j=1;j<=tList.size();j++){
            T t= tList.get(j-1);
            row=sheet.createRow(j);
            for(int i=0;i<keyList.size();i++){
                // 通过反射获取属性值
                String fieldName =keyList.get(i);// declaredFields[i].getName();
                String getMethodName="get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                Method declaredMethod = t.getClass().getDeclaredMethod(getMethodName);
                //执行方法
                Object fieldValue = declaredMethod.invoke(t);
                //判断是否为空
                if(fieldValue!=null &&  !"".equals(fieldValue)){
                    //判断属性值类型
                    if(fieldValue instanceof Integer){
                        row.createCell(i).setCellValue(Integer.valueOf(fieldValue.toString()));
                    }else if(fieldValue instanceof Double){
                        row.createCell(i).setCellValue(Double.valueOf(fieldValue.toString()));
                    }else if(fieldValue instanceof Date){
                        row.createCell(i).setCellValue(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(fieldValue));
                    }else {
                        row.createCell(i).setCellValue(fieldValue.toString());
                    }
                }else {
                    row.createCell(i).setCellValue("");
                }
            }
        }
//       自动设置列宽，要在在数据读入之后设置；
        for (int i = 0; i < valueList.size(); i++) {
            sheet.autoSizeColumn(i);
            //在自动适应的基础上增加宽度
            sheet.setColumnWidth(i,sheet.getColumnWidth(i)*17/10);
        }
        return workbook;
    }

    //解析json
    private static void resolveJson(String jsonStr) {
        JSONArray jsonArray = JSONArray.parseArray(jsonStr);
        Iterator iterator = jsonArray.iterator();
        // 遍历出初始化数据
        while (iterator.hasNext()) {
            JSONObject object = (JSONObject) iterator.next();
            // 取出json值，用于表头
            valueList.add(object.get("value") + "");
            // 取出json键，用于反射的取属性名
            keyList.add(object.get("key") + "");
        }
    }
    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<CompanyInfo> companyInfos=new ArrayList<>();
        CompanyInfo companyInfo=new CompanyInfo();
        companyInfo.setContent("dj");
        companyInfo.setId("3");
        companyInfo.setName("第一个");
        Date date =new Date();
        System.out.println(date);
        /*
         * 计算明天此刻的时间
         */
        long time =date.getTime();
        time = time+24*60*60*1000;
        date.setTime(time);
        companyInfo.setDate(date);
        companyInfos.add(companyInfo);
        CompanyInfo companyInfo1=new CompanyInfo();
        companyInfo1.setName("第二个");
        companyInfo1.setId("4");
        companyInfo1.setDate(new Date());
        companyInfo1.setContent("兴");
        companyInfos.add(companyInfo1);
        String json="[{\"key\":\"date\",\"value\":\"时间\"}{\"key\":\"content\",\"value\":\"内容\"}{\"key\":\"name\",\"value\":\"姓名\"}{\"key\":\"id\",\"value\":\"序号\"}]";
        HSSFWorkbook workbooka = new ExcelExportUtil().setExcel("pojoA", companyInfos,json);

        workbooka.write(new FileOutputStream(new File("D:\\1221\\pojoA.xls")));
    }
}
