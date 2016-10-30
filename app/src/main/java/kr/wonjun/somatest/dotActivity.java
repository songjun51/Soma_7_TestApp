package kr.wonjun.somatest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import kr.edcan.dispersionchart.DispersionChartView;
import kr.edcan.dispersionchart.Dot;

public class dotActivity extends AppCompatActivity implements View.OnClickListener {
    BluetoothSPP bt;
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
        bt = new BluetoothSPP(this);

        if (!bt.isBluetoothAvailable())

        {
            Toast.makeText(getApplicationContext()
                    , "블루투스를 켜주세요"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener()

        {
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "연결되었습니다", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() {
                Toast.makeText(getApplicationContext()
                        , "연결이끊겼습니다"
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() {
            }
        });
//        setup();
        bt.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
            public void onNewConnection(String name, String address) {
            }

            public void onAutoConnectionStarted() {
            }
        });

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {


            }
        });

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                Log.e("asdf","we have data");
                if (start == true) {
                    String[] bldata = message.split(",");
                    Log.e("asdf0", bldata[9]);
                    Log.e("asdf1", bldata[10]);
                    double temp1, temp2, temp3;

                    double temp4, temp5, temp6;

                    double temp7,temp8;


//                temp2= Integer.parseInt(bldata[1]);
                    temp1 = Double.parseDouble(bldata[9]);
                    temp4 = Double.parseDouble(bldata[10]);
                    temp2 = temp1 / 100;
                    temp5 = temp4 / 100;
//                    temp3 = (temp2 + 1) * 50;
//                    temp6 = (temp5 + 1) * 50;
                    temp3 = (temp2 * 5)+50;
                    temp6 = (temp5 * 5)+50;
                    if (start == true) {
                        startBtn.setText(temp2 + " , " + temp5);
                        dpView.addDot(new Dot((float) temp3, (float) temp6, dotColor[colorCnt]));
                        dpView.invalidate();
                        Log.e("asdf", "x : " + temp3 + " y : " + temp6 + " dotColor : " + colorCnt);
                    }
                }

            }

        });
// -10 * 5
        // 10 *5

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


//    public void onPause() {
//        super.onPause();
//        bt.stopService();
//    }

//    public void onStart() {
//        super.onStart();
//        if (!bt.isBluetoothEnabled()) {
//            bt.enable();
//        } else {.
//            if (!bt.isServiceAvailable()) {
//                bt.setupService();
//                bt.startService(BluetoothState.DEVICE_OTHER);
//                setup();
//            }
//        }
//    }

//    public void setup() {
//        bt.autoConnect("wnjungod");
//
//    }

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

}