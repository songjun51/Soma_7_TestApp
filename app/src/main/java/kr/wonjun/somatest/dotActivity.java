package kr.wonjun.somatest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import kr.edcan.dispersionchart.DispersionChartView;
import kr.edcan.dispersionchart.Dot;

public class dotActivity extends AppCompatActivity {
    float x, y;
    BluetoothSPP bt;
    DispersionChartView dpView;
    boolean start = false;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot);

        dpView = (DispersionChartView) findViewById(R.id.dpChartView);
        addBtn = (Button) findViewById(R.id.activity_dot_btn);

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

                String[] bldata = message.split(",");
                x = ((float) Float.parseFloat(String.valueOf(Integer.parseInt(bldata[0])/100)) + 1) * 50;
                y = ((float) Float.parseFloat(String.valueOf(Integer.parseInt(bldata[1])/100)) + 1) * 50;
                Toast.makeText(dotActivity.this, x + "," + y, Toast.LENGTH_SHORT).show();

                if (start == true) {
                    dpView.addDot(new Dot(x, y));
                    addBtn.setText("작동중, x : " + x + "y : " + y);
                }
                if (start == false)
                    addBtn.setText("멈춤");
            }

        });


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

    public void setup() {
        bt.autoConnect("wnjun");

    }

    public void onClick(View v) {
        if (start == true)
            start = false;

        else
            start = true;
        Toast.makeText(this, "버튼클릭됨.", Toast.LENGTH_SHORT).show();

    }
}

