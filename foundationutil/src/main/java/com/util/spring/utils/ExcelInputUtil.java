package com.util.spring.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Lan.Chen on 2018/11/1
 */
@RestController
@RequestMapping("/company")
public class ExcelInputUtil {
    private static final String XLS="xls";
    private static final String XLSX="xlsx";

    /**
     * 取得workbook,无论是2003版本还是2007版本 这是面向接口编程的好处
     * @param filePath
     * @param file
     * @return
     * @throws IOException
     */
    private static Workbook getWorkbook(String filePath, MultipartFile file) throws IOException {
        InputStream is;
        Workbook workbook=null;
        if (filePath==null||filePath.isEmpty()){
            is=file.getInputStream();
            String fileName=file.getOriginalFilename().toLowerCase();
            if(fileName.endsWith(XLS)){
                workbook=new HSSFWorkbook(is);
            }else if(fileName.endsWith(XLSX)){
                workbook=new XSSFWorkbook(is);
            }
        }else if(file.isEmpty()){
            is=new FileInputStream(filePath);
            if(filePath.endsWith(XLS)){
                workbook=new HSSFWorkbook(is);
            }else if(filePath.endsWith(XLSX)){
                workbook=new XSSFWorkbook(is);
            }
        }
        return workbook;
    }

    /**
     * 检查是否是excel文件
     * @param fileName
     * @return
     */
    public static boolean checkIfExcel(String fileName){
        fileName=fileName.toLowerCase();
        if(!(fileName.endsWith(XLS)||fileName.endsWith(XLSX))){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 读取excel
     * @param filePath
     * @param file
     * @throws IOException
     */
    public static void readExcel(String filePath,MultipartFile file) throws IOException {
        Workbook workbook=getWorkbook(filePath,file);
        for(int sheetNum=0;sheetNum<workbook.getNumberOfSheets();sheetNum++){
            Sheet sheet= workbook.getSheetAt(sheetNum);
            if(sheet==null){
                continue;
            }
            int lastRow=sheet.getLastRowNum();
            //跳过表头
            for(int rowNum=1;rowNum<=lastRow;rowNum++){
                Row rowCurrent=sheet.getRow(rowNum);//当前行
                for(int cellNum=0;cellNum<=rowCurrent.getLastCellNum();cellNum++){
                    Cell cellCurrent= rowCurrent.getCell(cellNum);//当前单元格
                    String cellValue=getCellValue(cellCurrent,true);
                    System.out.print(cellValue+"\t");
                }
            }
        }
    }

    private static String getCellValue(Cell cell, boolean treatAsStr) {
        if(cell==null){
            return "";
        }
        /**
         * 数字文本会被读错 如`1`变成`1.0`,为true时候就不会读错
         */
        if(treatAsStr){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

}
