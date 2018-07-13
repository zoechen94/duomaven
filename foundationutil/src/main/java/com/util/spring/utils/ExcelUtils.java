package com.util.spring.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
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

    public static Workbook readSheet(Workbook workbook) {
        Sheet sheet=workbook.getSheetAt(0);
        //某一行字段
        Row row=sheet.getRow(0);
        for(Cell c:row){
            System.out.print(getCellValue(c));
        }
        return workbook;
    }

    public static String getCellValue(Cell cell){

        if(cell == null) return "";

        if(cell.getCellType() == Cell.CELL_TYPE_STRING){

            return cell.getStringCellValue();

        }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){

            return String.valueOf(cell.getBooleanCellValue());

        }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){

            return cell.getCellFormula() ;

        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){

            return String.valueOf(cell.getNumericCellValue());

        }
        return "";
    }

    //apache poi 插入图片至Excel
    public static Workbook createPictureInExcel(String picturePath,Workbook workbook,Sheet sheet) throws IOException {
        InputStream is=new FileInputStream(picturePath);
        byte[] bytes= IOUtils.toByteArray(is);
        int picture=workbook.addPicture(bytes,Workbook.PICTURE_TYPE_JPEG);
        CreationHelper helper=workbook.getCreationHelper();
        Drawing drawing=sheet.createDrawingPatriarch();
        ClientAnchor anchor=helper.createClientAnchor();
        //图片插入坐标
        anchor.setCol1(1);
        anchor.setRow1(1);
        anchor.setCol2(9);
        anchor.setRow2(19);
        //插入图片
        Picture pict=drawing.createPicture(anchor,picture);
        pict.resize();
        return workbook;
    }

}
