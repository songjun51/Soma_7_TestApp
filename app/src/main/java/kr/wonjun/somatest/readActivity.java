//
//
//
//
//package kr.wonjun.somatest;
//
//import android.support.v4.content.PermissionChecker;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import app.akexorcist.bluetotohspp.library.BluetoothSPP;
//
//public class ReadActivity extends AppCompatActivity implements View.OnClickListener{
//    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
//    String[] btList = {"0", "0", "0", "0", "0", "0", "0", "0", "0"};
//    BluetoothSPP bt;
//
//
//    String recive;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_read);
//        btn1 = (Button) findViewById(R.id.activity_read_btn_1);
//        btn2 = (Button) findViewById(R.id.activity_read_btn_2);
//        btn3 = (Button) findViewById(R.id.activity_read_btn_3);
//        btn4 = (Button) findViewById(R.id.activity_read_btn_4);
//        btn5 = (Button) findViewById(R.id.activity_read_btn_5);
//        btn6 = (Button) findViewById(R.id.activity_read_btn_6);
//        btn7 = (Button) findViewById(R.id.activity_read_btn_7);
//        btn8 = (Button) findViewById(R.id.activity_read_btn_8);
//        btn9 = (Button) findViewById(R.id.activity_read_btn_9);
//
//        btn1.setOnClickListener(this);
//        btn2.setOnClickListener(this);
//        btn3.setOnClickListener(this);
//        btn4.setOnClickListener(this);
//        btn5.setOnClickListener(this);
//        btn6.setOnClickListener(this);
//        btn7.setOnClickListener(this);
//        btn8.setOnClickListener(this);
//        btn9.setOnClickListener(this);
//
//        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
//            @Override
//            public void onDataReceived(byte[] data, String message) {
//                recive=message;
//
//            }
//        }
//         "" +
//                 "");
//
//
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//}
//j,,,