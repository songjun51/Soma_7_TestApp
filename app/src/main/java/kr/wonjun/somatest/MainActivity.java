package kr.wonjun.somatest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

import static android.R.id.input;

public class MainActivity extends AppCompatActivity {

    BluetoothSPP bt;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ListView lv;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        alert = new AlertDialog.Builder(getApplicationContext());



        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);
        list = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            list.add(Integer.toString(i) + "꾹 눌러 메모해주세요 하악");
        }
//
        bt = new BluetoothSPP(this);
        if (!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext()
                    , "블루투스를 켜주세요"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }
        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
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
                Toast.makeText(MainActivity.this, "연결실패", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

            }
        });
        //만약에 온 값이 있다면 토스트로 출력


        lv.setOnItemClickListener(listener);
//        lv.setOnItemLongClickListener((AdapterView.OnItemLongClickListener) Longlistener);


    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            bt.send(String.valueOf(position), true);
            alert.setTitle("정민이형 똥멍청이");
            alert.setMessage("수정할 내용을 입력");
            final EditText input = new EditText(getApplicationContext());
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    bt.send(input.getText().toString(), true);
                }
            });
            alert.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });


            alert.show();


            bt.send(Integer.toString(position), true);
// 클릭값 블투로전송
        }
    };

    AdapterView.OnItemClickListener Longlistener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            alert.setTitle("정민이형 똥멍청이");
            alert.setMessage("수정할 내용을 입력");
            final EditText input = new EditText(getApplicationContext());
            alert.setView(input);
            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    list.set(position, position + " : " + input.getText().toString());
                }

            });

            // 길게 클릭시 포지션 받아서 포지션 : 원하는 텍스트로 변경
            alert.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });
            alert.show();//다이얼로그 끝

// 클릭값 수정
        }
    };

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
