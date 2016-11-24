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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.utils.ConnectionSharingAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import kr.edcan.dispersionchart.Dot;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

public class ExcelActivity extends AppCompatActivity implements View.OnClickListener {
    int click = 0;
    private static final String TAG = "BLE";
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

    int[] coordinates;


    RxBleDevice device;
    RxBleClient rxBleClient;
    BluetoothPacket bluetoothPacket;
    private Observable<RxBleConnection> connectionObservable;
    private static final String macAddress = "20:CD:39:7B:FC:5F";   // 기기를 찾기 위한 맥어드레스
    // F4:B8:5E:F0:57:E5
    UUID characteristicUUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");  // 통신을 위한 UUID
    Timer timer;

    TimerTask timerTask = new TimerTask() {
        public void run() {
            Log.d(TAG, "실시간모드 요청 패킷 전송");
            if (isBluetoothConnected()) {
                connectionObservable
                        .flatMap(rxBleConnection -> rxBleConnection.writeCharacteristic(characteristicUUID, bluetoothPacket.makeRealTimeModePacket()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bytes -> {
                            Log.d(TAG, "실시간모드 전송 성공");
                        }, throwable -> {
                            Log.d(TAG, "실시간모드 전송 실패" + throwable);
                        });
            }
        }
    };

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


        // BLE 관련입니다.
        bluetoothPacket = new BluetoothPacket();
        rxBleClient = RxBleClient.create(getApplicationContext());
        device = rxBleClient.getBleDevice(macAddress);  // 디바이스를 얻어옵니다.
        PublishSubject<Void> disconnectTriggerSubject = PublishSubject.create();

        // 디바이스의 연결을 공유하는 connectionObservable. 이것을 활용해서 나중에 읽기 쓰기를 합니다.
        connectionObservable = device
                .establishConnection(getApplicationContext(), true) // 오른쪽 boolean 인자는 자동연결 관련입니다.
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(disconnectTriggerSubject)
                .compose(new ConnectionSharingAdapter());

        setBluetoothConnect();  // 블루투스 연결을 실제로 시작
        setBluetoothRead();     // 블루투스 읽기를 시작

        timer = new Timer();
        timer.schedule(timerTask, 1000, 3000);

//        startBtn.setOnClickListener(this);
//        stopBtn.setOnClickListener(this);
//        dotColor = getResources().getStringArray(R.array.dotColor);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


//        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
//            public void onDataReceived(byte[] data, String message) {
//                receive = message;
//                String[] bldata = message.split(",");
//
//                for (int i = 0; i < bldata.length; i++) {
//                    btList[i] = bldata[i];
//                }
//                Log.e("asdf", message);
//                tv.setText("" + receive);
//
////                if(btList[0]=="@"||btList[10]=="!") {
//                leftup.setText(btList[0]);
//                up.setText(btList[1]);
//                rightup.setText(btList[2]);
//                left.setText(btList[3]);
//                center.setText(btList[4]);
//                right.setText(btList[5]);
//                leftdown.setText(btList[6]);
//                down.setText(btList[7]);
//                rightdown.setText(btList[8]);
//
//
//
//            }
//        });

//    }


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
        } else if (id == R.id.menu_item_dot) {
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
        cell.setCellValue(coordinates[0]);

        cell = row.createCell(2);
        cell.setCellValue(coordinates[1]);

        cell = row.createCell(3);
        cell.setCellValue(coordinates[2]);

        cell = row.createCell(4);
        cell.setCellValue(coordinates[3]);

        cell = row.createCell(5);
        cell.setCellValue(coordinates[4]);

        cell = row.createCell(6);
        cell.setCellValue(coordinates[5]);

        cell = row.createCell(7);
        cell.setCellValue(coordinates[6]);

        cell = row.createCell(8);
        cell.setCellValue(coordinates[7]);

        cell = row.createCell(9);
        cell.setCellValue(coordinates[8]);
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

    public boolean isBluetoothConnected() {
        return device.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTED;
    }

    public void setBluetoothConnect() {
        connectionObservable.subscribe(
                characteristicValue -> {
                    // Read characteristic value.
                    Log.d(TAG, "블루투스가 연결되었다.");
                },
                throwable -> {
                    // Handle an error here.
                    Log.d(TAG, "블루투스가 연결 실패. 내용 : " + throwable);
                    Log.d(TAG, "재연결을 시도합니다.");
                    setBluetoothConnect();
                }
        );
    }


    public void setBluetoothRead() {
        // read 와 관련된 부분입니다. 리스너라고 생각하세요.
        connectionObservable.flatMap(rxBleConnection -> rxBleConnection.setupNotification(characteristicUUID))
                .doOnNext(notificationObservable -> {
                    // Notification has been set up
                    // 리스너가 세팅된 후 동작을 여기에 적습니다.
                })
                .flatMap(notificationObservable -> notificationObservable) // <-- Notification has been set up, now observe value changes.
                .subscribe(
                        bytes -> {
                            // Given characteristic has been changes, here is the value.// Given characteristic has been changes, here is the value.
                            Log.d(TAG, "값이 들어왔습니다.");
                            bluetoothPacket.decodePacket(bytes); // 패킷을 디코딩한다.
                            if (bluetoothPacket.getIsPacketCompleted()) { // 패킷이 완성되었다면
                                Log.d(TAG, "패킷을 처리합니다.");



                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    coordinates = bluetoothPacket.getValue();
                                                    leftup.setText(String.valueOf(coordinates[0]));
                                                    up.setText(String.valueOf(coordinates[1]));
                                                    rightup.setText(String.valueOf(coordinates[2]));
                                                    left.setText(String.valueOf(coordinates[3]));
                                                    center.setText(String.valueOf(coordinates[4]));
                                                    right.setText(String.valueOf(coordinates[5]));
                                                    leftdown.setText(String.valueOf(coordinates[6]));
                                                    down.setText(String.valueOf(coordinates[7]));
                                                    rightdown.setText(String.valueOf(coordinates[8]));

                                                    Log.e("asdf","good");
                                                }
                                            });
                                        }
                                    }).start();


                            }
                        },
                        throwable -> {
                            Log.d(TAG, "read에 예외가 발생했습니다. 내용 : " + throwable);
                            Log.d(TAG, "Read 재연결을 시도합니다.");
                            setBluetoothRead();
                        }
                );
    }

}


