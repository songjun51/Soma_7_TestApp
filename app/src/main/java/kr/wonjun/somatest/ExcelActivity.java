package kr.wonjun.somatest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

import static android.R.id.list;

public class ExcelActivity extends AppCompatActivity {
    int click=0;
    String bt1="0",bt2="0",bt3="0",bt4="0",bt5="0",bt6="0",bt7="0",bt8="0",bt9="0";
    BluetoothSPP bt;
    String receive;
    Button left,right,center,down,up,leftup,leftdown,rightup,rightdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execl);

        left = (Button) findViewById(R.id.activity_execl_btn_left);
        right = (Button) findViewById(R.id.activity_execl_btn_right);
        up = (Button) findViewById(R.id.activity_execl_btn_up);
        down = (Button) findViewById(R.id.activity_execl_btn_down);
        center = (Button) findViewById(R.id.activity_execl_btn_center);
        leftup = (Button) findViewById(R.id.activity_execl_btn_leftup);
        leftdown = (Button) findViewById(R.id.activity_execl_btn_leftdown);
        rightup = (Button) findViewById(R.id.activity_execl_btn_leftup);
        rightdown = (Button) findViewById(R.id.activity_execl_btn_leftdown);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CustomVo> list = new ArrayList<CustomVo>();
                click=1;
                list.add(new CustomVo(click,bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9));



            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExcelActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExcelActivity.this, "up", Toast.LENGTH_SHORT).show();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExcelActivity.this, "down", Toast.LENGTH_SHORT).show();
            }
        });
        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExcelActivity.this, "center", Toast.LENGTH_SHORT).show();
            }
        });
        leftup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExcelActivity.this, "leftup", Toast.LENGTH_SHORT).show();
            }
        });
        leftdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExcelActivity.this, "leftdown", Toast.LENGTH_SHORT).show();
            }
        });
        rightup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExcelActivity.this, "rightup", Toast.LENGTH_SHORT).show();
            }
        });
        rightdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExcelActivity.this, "rightdown", Toast.LENGTH_SHORT).show();
            }
        });


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

        bt.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
            public void onNewConnection(String name, String address) {
            }

            public void onAutoConnectionStarted() {
            }
        });
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                receive = message;
                if (receive.equals("to=true")) {
                } else if (receive.equals("to=false")) {

                }
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
        bt.autoConnect("wonjungod");
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
            startActivity(new Intent(getApplicationContext(), ReadActivity.class));
            return true;
        } else if (id == R.id.menu_item_excel) {
            startActivity(new Intent(getApplicationContext(), ExcelActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

