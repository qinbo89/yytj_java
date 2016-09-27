package com.hongbao.dal.util;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by shengshan.tang on 8/20/2015 at 2:00 PM
 * Excel 导出工具类
 */
public class ExcelUtils {

    /**
     * 导出
     * @param bookTitle
     * @param headers
     * @param dataList
     * @param response
     */
    public static void exportExcel(String bookTitle,String [] headers,List<List<String>> dataList,HttpServletResponse response){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(bookTitle);

        // Create a row and put some cells in it. Rows are 0 based.
        int rowNum = 0;
        HSSFRow row = sheet.createRow(rowNum);
        rowNum++;

        // Create a cell and put a date value in it.  The first cell is not styled as a date.
        for(int i = 0; i < headers.length; i++){
            String header = headers[i];
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(header);
        }


        for(List<String> rowList : dataList){
            row = sheet.createRow(rowNum);
            rowNum++;
            for(int i = 0; i < rowList.size(); i++){
                String rowVal = rowList.get(i);
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(rowVal);
            }
        }

        // Write the output to a file
        OutputStream os = null;
        try{
            String fileName = bookTitle+".xls";
            os =  response.getOutputStream();
            // 编码处理，对于linux 操作系统 （ linux默认 utf-8,windows 默认 GBK)
            String defaultEncoding = System.getProperty("file.encoding");
            if (defaultEncoding != null && defaultEncoding.equals("UTF-8")) {
                fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
            } else {
                fileName = new String(fileName.getBytes(), "iso-8859-1");
            }
            // 设置response的Header
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            wb.write(os);
        }catch(Exception e){
            throw new RuntimeException(e);
        }finally {
            IOUtils.closeQuietly(os);
        }



    }

    public static void main(String[] args) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("提现申请");

        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow(0);

        // Create a cell and put a date value in it.  The first cell is not styled as a date.
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(new Date());


        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("d:/workbook.xls");
        wb.write(fileOut);
        fileOut.close();
    }

}
