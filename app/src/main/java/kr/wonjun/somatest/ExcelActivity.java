package kr.wonjun.somatest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

public class ExcelActivity extends AppCompatActivity implements View.OnClickListener {
    int click = 0;
    String[] btList;
    BluetoothSPP bt;
    String receive;
    Button left, right, center, down, up, leftup, leftdown, rightup, rightdown, saveExcel, ReadBtn;

    Workbook wb;
    int rowIdx = 1;
    Cell cell = null;
    Sheet sheet1 = null;
    Row row = null;
    File file;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execl);
//        bluetoothFirtSetting();
        firstExcelSetting();
        left = (Button) findViewById(R.id.activity_execl_btn_left);
        right = (Button) findViewById(R.id.activity_execl_btn_right);
        up = (Button) findViewById(R.id.activity_execl_btn_up);
        down = (Button) findViewById(R.id.activity_execl_btn_down);
        center = (Button) findViewById(R.id.activity_execl_btn_center);
        leftup = (Button) findViewById(R.id.activity_execl_btn_leftup);
        leftdown = (Button) findViewById(R.id.activity_execl_btn_leftdown);
        rightup = (Button) findViewById(R.id.activity_execl_btn_rightup);
        rightdown = (Button) findViewById(R.id.activity_execl_btn_rightdown);
//        saveExcel = (Button) findViewById(R.id.activity_execl_saveExecl);
        tv = (TextView) findViewById(R.id.activity_execl_seeData);

        leftup.setOnClickListener(this);
        left.setOnClickListener(this);
        leftdown.setOnClickListener(this);
        center.setOnClickListener(this);
        right.setOnClickListener(this);
        rightdown.setOnClickListener(this);
        rightup.setOnClickListener(this);
        up.setOnClickListener(this);
        down.setOnClickListener(this);
//        saveExcel.setOnClickListener(this);
        bt=new BluetoothSPP(this);

        btList=new String[11];
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                receive = message;
                String[] bldata = message.split(",");

                for (int i = 0; i < bldata.length; i++) {
                    btList[i] = bldata[i];
                }
                Log.e("asdf", message);
                tv.setText("" + receive);

//                if(btList[0]=="@"||btList[10]=="!") {
                leftup.setText(btList[0]);
                up.setText(btList[1]);
                rightup.setText(btList[2]);
                left.setText(btList[3]);
                center.setText(btList[4]);
                right.setText(btList[5]);
                leftdown.setText(btList[6]);
                down.setText(btList[7]);
                rightdown.setText(btList[8]);
//                }
//                else {
//                    Toast.makeText(ExcelActivity.this, "에러!", Toast.LENGTH_SHORT).show();
//                }
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
//                    dialog.setTitle("에러!");
//                    dialog.setMessage("에러가 계속된다면 다음 화면을 캡쳐해 A/S센터에 문의해주세요 Tel : 010-3256-8530);\n에러사항 : "+message);
//                    dialog.show();
//
//
//                }


            }
        });

    }


    public void onDestroy() {
        super.onDestroy();
        bt.stopService();
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            bt.enable();
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
            } else {
                Toast.makeText(getApplicationContext()
                        , "블루투스 켜주세요"
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void setup() {
        bt.autoConnect("wnjungod");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_write) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return true;
        } else if (id == R.id.menu_item_read) {
            startActivity(new Intent(getApplicationContext(), TerminalActivity.class));
            return true;
        } else if (id == R.id.menu_item_excel) {
            startActivity(new Intent(getApplicationContext(), ExcelActivity.class));
            return true;
        }else if (id == R.id.menu_item_dot) {
            startActivity(new Intent(getApplicationContext(), dotActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void firstExcelSetting() {
        file = new File(this.getExternalFilesDir(null), "myExcel.xls");
        wb = new HSSFWorkbook();
        // New Sheet
        sheet1 = wb.createSheet("Shit1");

        row = sheet1.createRow(0);

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
    }

//    private boolean saveExcelFile(Context context, String fileName) {
//
//        // New Workbook
//
//
//        // Generate column headings
//        row = sheet1.createRow(0);
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
//        cell.setCellValue(click);
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_execl_btn_left:
                click = 4;
                Log.e("shit", "왼쪽버튼");
                writebt();
                break;
            case R.id.activity_execl_btn_leftdown:
                click = 7;
                writebt();
                break;
            case R.id.activity_execl_btn_leftup:
                click = 1;
                writebt();
                break;
            case R.id.activity_execl_btn_right:
                click = 6;
                writebt();
                break;
            case R.id.activity_execl_btn_rightdown:
                click = 9;
                writebt();
                break;
            case R.id.activity_execl_btn_rightup:
                click = 3;
                writebt();
                break;
            case R.id.activity_execl_btn_center:
                click = 5;
                writebt();
                break;
            case R.id.activity_execl_btn_up:
                click = 2;
                writebt();
                break;
            case R.id.activity_execl_btn_down:
                click = 8;
                break;
        }
    }


    private void writebt() {
        row = sheet1.createRow(rowIdx);
        cell = row.createCell(0);
        cell.setCellValue(click);

        cell = row.createCell(1);
        cell.setCellValue(btList[0]);

        cell = row.createCell(2);
        cell.setCellValue(btList[1]);

        cell = row.createCell(3);
        cell.setCellValue(btList[2]);

        cell = row.createCell(4);
        cell.setCellValue(btList[3]);

        cell = row.createCell(5);
        cell.setCellValue(btList[4]);

        cell = row.createCell(6);
        cell.setCellValue(btList[5]);

        cell = row.createCell(7);
        cell.setCellValue(btList[6]);

        cell = row.createCell(8);
        cell.setCellValue(btList[7]);

        cell = row.createCell(9);
        cell.setCellValue(btList[8]);
        rowIdx++;
        Log.e("fuck", "값입력");
        saveExcleFile();
    }

    private void saveExcleFile() {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        Toast.makeText(this, "값저장완료", Toast.LENGTH_SHORT).show();
        Log.e("fuck", "저장완료");
    }

//    private void bluetoothFirtSetting() {
//        bt = new BluetoothSPP(this);
//
//        if (!bt.isBluetoothAvailable())
//
//        {
//            Toast.makeText(getApplicationContext()
//                    , "블루투스를 켜주세요"
//                    , Toast.LENGTH_SHORT).show();
//            finish();
//        }
//
//        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener()
//
//        {
//            public void onDeviceConnected(String name, String address) {
//                Toast.makeText(getApplicationContext()
//                        , "연결되었습니다", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onDeviceDisconnected() {
//                Toast.makeText(getApplicationContext()
//                        , "연결이끊겼습니다"
//                        , Toast.LENGTH_SHORT).show();
//            }
//
//            public void onDeviceConnectionFailed() {
//            }
//        });
//
//        bt.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
//            public void onNewConnection(String name, String address) {
//            }
//
//            public void onAutoConnectionStarted() {
//            }
//        });
//    }
}


