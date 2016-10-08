package kr.wonjun.somatest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by swj85 on 2016-10-09.
 */

public class ExcelWriter {


    public void xlsWiter(List<CustomVo> list) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet();


        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;

        cell = row.createCell(0);
        cell.setCellValue("status");

        cell = row.createCell(1);
        cell.setCellValue("LeftUp");

        cell = row.createCell(2);
        cell.setCellValue("Up");

        cell = row.createCell(3);
        cell.setCellValue("RightUp");

        cell = row.createCell(4);
        cell.setCellValue("Left");

        cell = row.createCell(5);
        cell.setCellValue("Center");

        cell = row.createCell(6);
        cell.setCellValue("Right");

        cell = row.createCell(7);
        cell.setCellValue("LeftDown");

        cell = row.createCell(8);
        cell.setCellValue("Down");

        cell = row.createCell(9);
        cell.setCellValue("RightDown");


        //리스트의 사이즈만큼 row 생성
        CustomVo vo;
        for (int rowIdx = 0; rowIdx < list.size(); rowIdx++) {
            vo = list.get(rowIdx);

            row = sheet.createRow(rowIdx + 1);

            cell = row.createCell(0);
            cell.setCellValue(vo.getStatus());

            cell = row.createCell(1);
            cell.setCellValue(vo.getLeftUp());

            cell = row.createCell(2);
            cell.setCellValue(vo.getUp());

            cell = row.createCell(3);
            cell.setCellValue(vo.getRightUp());

            cell = row.createCell(4);
            cell.setCellValue(vo.getLeft());

            cell = row.createCell(5);
            cell.setCellValue(vo.getCenter());

            cell = row.createCell(6);
            cell.setCellValue(vo.getRight());

            cell = row.createCell(7);
            cell.setCellValue(vo.getLeftDown());

            cell = row.createCell(8);
            cell.setCellValue(vo.getDown());

            cell = row.createCell(9);
            cell.setCellValue(vo.getRightDown());

        }

        File file = new File("C:\\excel\\wonjunWite.xls");
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            workbook.write(fos);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(workbook!=null) workbook.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void xlsxWiter(List<CustomVo> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet();


        XSSFRow row = sheet.createRow(0);
        XSSFCell cell;

        cell = row.createCell(0);
        cell.setCellValue("status");

        cell = row.createCell(1);
        cell.setCellValue("LeftUp");

        cell = row.createCell(2);
        cell.setCellValue("Up");

        cell = row.createCell(3);
        cell.setCellValue("RightUp");

        cell = row.createCell(4);
        cell.setCellValue("Left");

        cell = row.createCell(5);
        cell.setCellValue("Center");

        cell = row.createCell(6);
        cell.setCellValue("Right");

        cell = row.createCell(7);
        cell.setCellValue("LeftDown");

        cell = row.createCell(8);
        cell.setCellValue("Down");

        cell = row.createCell(9);
        cell.setCellValue("RightDown");


        //리스트의 사이즈만큼 row 생성
        CustomVo vo;
        for (int rowIdx = 0; rowIdx < list.size(); rowIdx++) {
            vo = list.get(rowIdx);

            row = sheet.createRow(rowIdx + 1);

            cell = row.createCell(0);
            cell.setCellValue(vo.getStatus());

            cell = row.createCell(1);
            cell.setCellValue(vo.getLeftUp());

            cell = row.createCell(2);
            cell.setCellValue(vo.getUp());

            cell = row.createCell(3);
            cell.setCellValue(vo.getRightUp());

            cell = row.createCell(4);
            cell.setCellValue(vo.getLeft());

            cell = row.createCell(5);
            cell.setCellValue(vo.getCenter());

            cell = row.createCell(6);
            cell.setCellValue(vo.getRight());

            cell = row.createCell(7);
            cell.setCellValue(vo.getLeftDown());

            cell = row.createCell(8);
            cell.setCellValue(vo.getDown());

            cell = row.createCell(9);
            cell.setCellValue(vo.getRightDown());

        }

        File file = new File("C:\\excel\\wonjunWite.xls");
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            workbook.write(fos);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(workbook!=null) workbook.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}
