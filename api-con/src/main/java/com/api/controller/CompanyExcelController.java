package com.api.controller;

import com.util.spring.resultInfo.CompanyInfo;
import com.util.spring.resultInfo.ResultData;
import com.util.spring.utils.DateUtil;
import com.util.spring.utils.ExcelExportUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lan.Chen on 2018/10/31
 */
@RestController
@RequestMapping("/company")
public class CompanyExcelController {
    public static final  String companyJson="[{\"key\":\"date\",\"value\":\"时间\"}{\"key\":\"content\",\"value\":\"内容\"}{\"key\":\"name\",\"value\":\"姓名\"}{\"key\":\"id\",\"value\":\"序号\"}]";

    @GetMapping("/export.do")
    @ApiOperation("导出excel")
    public ResultData exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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

        HSSFWorkbook workbooka =  new ExcelExportUtil().setExcel("pojoA", companyInfos,companyJson);
        String fileName="公司" + DateUtil.date2StringNoKong(new Date());
        System.out.println(fileName);
        response.reset();//清空输出流
        response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8")+".xls");
        response.setContentType("application/msexcel");// 定义输出类型
        OutputStream out=response.getOutputStream();

        workbooka.write(out);
        out.flush();
        out.close();
        return ResultData.success("输出成功");
    }
}
