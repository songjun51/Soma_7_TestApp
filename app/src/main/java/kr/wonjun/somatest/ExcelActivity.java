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
    int click = 0;
    String[] btList = {"0", "0", "0", "0", "0", "0", "0", "0", "0"};
    BluetoothSPP bt;
    String receive;
    Button left, right, center, down, up, leftup, leftdown, rightup, rightdown;
    List<CustomVo> list;

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
        list = new ArrayList<CustomVo>();

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                String[] bldata = message.split(",");

                for (int i = 0; i < bldata.length; i++) {
                    btList[i] = bldata[i];
                }
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = 4;
                list.add(new CustomVo(click, btList[0], btList[1], btList[2], btList[3], btList[4], btList[5], btList[6], btList[7], btList[8]));// 블투값을 넣음, 자세상태, 각셀 값
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = 6;
                list.add(new CustomVo(click, btList[0], btList[1], btList[2], btList[3], btList[4], btList[5], btList[6], btList[7], btList[8]));


            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = 2;
                list.add(new CustomVo(click, btList[0], btList[1], btList[2], btList[3], btList[4], btList[5], btList[6], btList[7], btList[8]));
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = 8;
                list.add(new CustomVo(click, btList[0], btList[1], btList[2], btList[3], btList[4], btList[5], btList[6], btList[7], btList[8]));
            }
        });
        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = 5;
                list.add(new CustomVo(click, btList[0], btList[1], btList[2], btList[3], btList[4], btList[5], btList[6], btList[7], btList[8]));
            }
        });
        leftup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = 1;
                list.add(new CustomVo(click, btList[0], btList[1], btList[2], btList[3], btList[4], btList[5], btList[6], btList[7], btList[8]));
            }
        });
        leftdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = 7;
                list.add(new CustomVo(click, btList[0], btList[1], btList[2], btList[3], btList[4], btList[5], btList[6], btList[7], btList[8]));
            }
        });
        rightup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = 3;
                list.add(new CustomVo(click, btList[0], btList[1], btList[2], btList[3], btList[4], btList[5], btList[6], btList[7], btList[8]));
            }
        });
        rightdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = 9;
                list.add(new CustomVo(click, btList[0], btList[1], btList[2], btList[3], btList[4], btList[5], btList[6], btList[7], btList[8]));
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

