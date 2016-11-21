package kr.wonjun.somatest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.polidea.rxandroidble.RxBleClient;
import com.polidea.rxandroidble.RxBleConnection;
import com.polidea.rxandroidble.RxBleDevice;
import com.polidea.rxandroidble.utils.ConnectionSharingAdapter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import kr.edcan.dispersionchart.DispersionChartView;
import kr.edcan.dispersionchart.Dot;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

public class dotActivity extends AppCompatActivity implements View.OnClickListener {
    //BLE
    private static final String TAG = "BLE";
    RxBleDevice device;
    RxBleClient rxBleClient;
    BluetoothPacket bluetoothPacket;
    private Observable<RxBleConnection> connectionObservable;
    private static final String macAddress = "88:4A:EA:76:BD:51";   // 기기를 찾기 위한 맥어드레스
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


    DispersionChartView dpView;
    boolean start = false;
    Button startBtn, stopBtn, resetBtn;
    String[] dotColor;
    int colorCnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot);

        dpView = (DispersionChartView) findViewById(R.id.dpChartView);
        startBtn = (Button) findViewById(R.id.activity_dot_start_btn);
        stopBtn = (Button) findViewById(R.id.activity_dot_stop_btn);
        resetBtn = (Button) findViewById(R.id.activity_dot_reset_btn);

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

        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        dotColor = getResources().getStringArray(R.array.dotColor);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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


    public void onPause() {
        super.onPause();
    }

    public void onStart() {
        super.onStart();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_dot_start_btn:
                start = true;
                Log.e("asdf","on");
                break;

            case R.id.activity_dot_stop_btn:
                Log.e("asdf", " dotColor : " + colorCnt);
                start = false;
                colorCnt += 1;
                if (colorCnt == 8)
                    colorCnt = 0;
                break;
            case R.id.activity_dot_reset_btn:
                dpView.deleteAllDots();
                break;
        }

    }

    public boolean isBluetoothConnected(){
        return device.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTED;
    }

    public void setBluetoothConnect(){
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

    public void setBluetoothRead(){
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
                            if(bluetoothPacket.getIsPacketCompleted()) { // 패킷이 완성되었다면
                                Log.d(TAG, "패킷을 처리합니다.");
                                if (start == true) {
                                    double[] coordinates = bluetoothPacket.getPosition();

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable(){
                                                @Override
                                                public void run() {
                                                    // 해당 작업을 처리함
                                                    startBtn.setText(bluetoothPacket.getPosition()[0] + " , " + bluetoothPacket.getPosition()[1]);
                                                    dpView.addDot(new Dot((float)coordinates[0], (float)coordinates[1], dotColor[colorCnt]));
                                                    dpView.invalidate();
                                                }
                                            });
                                        }
                                    }).start();

                                }
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