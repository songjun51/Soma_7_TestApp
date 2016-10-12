package kr.wonjun.somatest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

import static android.R.id.input;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    BluetoothSPP bt;
    Button cnnet;
    ListView list;
    AlertDialog.Builder alert;
    ArrayAdapter<String> Adapter;
    ArrayList<String> arDessert;
    int[] sendDataList = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        alert = new AlertDialog.Builder(getApplicationContext());
        arDessert = new ArrayList<String>();

        for (int i = 1; i <= 32; i++) {
            arDessert.add(String.valueOf(i));

        }

        cnnet=(Button) findViewById(R.id.activity_execl_saveExecl);
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arDessert);

        // 어댑터 연결
        list = (ListView) findViewById(R.id.lv);
        list.setAdapter(Adapter);

        // 리스트뷰 속성(없어도 구현 가능)
        // 항목을 선택하는 모드
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // 항목 사이의 구분선 지정
        list.setDivider(new ColorDrawable(Color.BLACK));
        // 구분선의 높이 지정
        list.setDividerHeight(1);

//

        //만약에 온 값이 있다면 토스트로 출력


        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                bt.send(String.valueOf(sendDataList[position]),true);


            }
        });


//        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                alert.setTitle("정민이형 똥멍청이");
//                alert.setMessage("수정할 내용을 입력");
//                final EditText input = new EditText(getApplicationContext());
//                alert.setView(input);
//                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        arDessert.set(position, position + " : " + input.getText().toString());
//                    }
//
//                });
//
//                return false;
//            }
//        });

////        lv.setOnItemLongClickListener((AdapterView.OnItemLongClickListener) Longlistener);
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

//        setup();

        bluetoothFirstSetting();


    }

    public void onItemClick(AdapterView<?> parent,View v, int position, long id) {

    }

    OnItemClickListener listener = new OnItemClickListener() {
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

//    AdapterView.OnItemClickListener Longlistener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

//            // 길게 클릭시 포지션 받아서 포지션 : 원하는 텍스트로 변경
//            alert.setNegativeButton("Cancel",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                            // Canceled.
//                        }
//                    });
//            alert.show();//다이얼로그 끝
//
////// 클릭값 수정
//        }

    

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
            startActivity(new Intent(getApplicationContext(), ReadActivity.class));
            return true;
        } else if (id == R.id.menu_item_excel) {
            startActivity(new Intent(getApplicationContext(), ExcelActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void bluetoothFirstSetting() {


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



    public void onClick(View v) {
        bt.autoConnect("wnjungod");
        Toast.makeText(this, "오토커넥", Toast.LENGTH_SHORT).show();
    }
}
