//package kr.wonjun.somatest;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Iterator;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//public class Excel{
//    private Button writeExcelButton;
//    private Button readExcelButton,readwriteButton;
//    Workbook wb = new HSSFWorkbook();
//
//    static String TAG = "ExelLog";
//    int rowIdx = 1;
//    Cell cell=null;
//    Sheet sheet1 = null;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity__excel_test);
//
//        writeExcelButton = (Button) findViewById(R.id.writeExcel2);
//        writeExcelButton.setOnClickListener(this);
//        readExcelButton = (Button) findViewById(R.id.readExcel2);
//        readExcelButton.setOnClickListener(this);
//        readwriteButton = (Button)findViewById(R.id.readwriteExcel);
//        readwriteButton.setOnClickListener(this);
//        wb = new HSSFWorkbook();
//        // New Sheet
//        sheet1 = wb.createSheet("Shit1");
//
//
//    }
//
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.writeExcel2:
//                saveExcelFile(this, "myExcel.xls");
//                break;
//            case R.id.readExcel2:
//                readExcelFile(this, "myExcel.xls");
//                break;
//            case R.id.readwriteExcel:
//                File file = new File(this.getExternalFilesDir(null), "myExcel.xls");
//                FileOutputStream os = null;
//
//                try {
//                    os = new FileOutputStream(file);
//                    wb.write(os);
//                    Log.w("FileUtils", "Writing file" + file);
//                } catch (IOException e) {
//                    Log.w("FileUtils", "Error writing " + file, e);
//                } catch (Exception e) {
//                    Log.w("FileUtils", "Failed to save file", e);
//                } finally {
//                    try {
//                        if (null != os)
//                            os.close();
//                    } catch (Exception ex) {
//                    }
//                }
//                break;
//        }
//    }
//
//    private boolean saveExcelFile(Context context, String fileName) {
//
//        // check if available and not read only
//        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
//            Log.e(TAG, "Storage not available or read only");
//            return false;
//        }
//
//
//        // New Workbook
//
//
//
//        // Generate column headings
//        Row row = sheet1.createRow(0);
//
//        cell = row.createCell(0);
//        cell.setCellValue("status");
//
//        cell = row.createCell(1);
//        cell.setCellValue("LeftUp");
//
//        cell = row.createCell(2);
//        cell.setCellValue("Up");
//
//        cell = row.createCell(3);
//        cell.setCellValue("RightUp");
//
//        cell = row.createCell(4);
//        cell.setCellValue("Left");
//
//        cell = row.createCell(5);
//        cell.setCellValue("Center");
//
//        cell = row.createCell(6);
//        cell.setCellValue("Right");
//
//        cell = row.createCell(7);
//        cell.setCellValue("LeftDown");
//
//        cell = row.createCell(8);
//        cell.setCellValue("Down");
//
//        cell = row.createCell(9);
//        cell.setCellValue("RightDown");
//
//
////        CustomVo vo;
//
//
//        row = sheet1.createRow(rowIdx);
//
//
//        cell = row.createCell(0);
//        cell.setCellValue(rowIdx);
//
//        cell = row.createCell(1);
//        cell.setCellValue("1");
//
//        cell = row.createCell(2);
//        cell.setCellValue("2");
//
//        cell = row.createCell(3);
//        cell.setCellValue("2");
//
//        cell = row.createCell(4);
//        cell.setCellValue("2");
//
//        cell = row.createCell(5);
//        cell.setCellValue("2");
//
//        cell = row.createCell(6);
//        cell.setCellValue("2");
//
//        cell = row.createCell(7);
//        cell.setCellValue("2");
//
//        cell = row.createCell(8);
//        cell.setCellValue("2");
//
//        cell = row.createCell(9);
//        cell.setCellValue("2");
//        rowIdx++;
//
//
//        // Create a path where we will place our List of objects on external
//        // storage
//        return true;
//
//    }
//
//
//    private void readExcelFile(Context context, String filename) {
//
//        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
//            Log.e(TAG, "Storage not available or read only");
//            return;
//        }
//
//        try {
//            // Creating Input Stream
//            File file = new File(context.getExternalFilesDir(null), filename);
//            FileInputStream myInput = new FileInputStream(file);
//
//            // Create a POIFSFileSystem object
//            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
//
//            // Create a workbook using the File System
//            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
//
//            // Get the first sheet from workbook
//            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
//
//            /** We now need something to iterate through the cells. **/
//            Iterator rowIter = mySheet.rowIterator();
//
//            while (rowIter.hasNext()) {
//                HSSFRow myRow = (HSSFRow) rowIter.next();
//                Iterator cellIter = myRow.cellIterator();
//                while (cellIter.hasNext()) {
//                    HSSFCell myCell = (HSSFCell) cellIter.next();
//                    Log.d(TAG, "Cell Value: " + myCell.toString());
//                    Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return;
//    }
//
//    public boolean isExternalStorageReadOnly() {
//        String extStorageState = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
//            return true;
//        }
//        return false;
//    }
//
//    public boolean isExternalStorageAvailable() {
//        String extStorageState = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
//            return true;
//        }
//        return false;
//    }
//}
