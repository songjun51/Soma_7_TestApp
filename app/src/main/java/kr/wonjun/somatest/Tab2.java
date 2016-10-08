package kr.wonjun.somatest;

/**
 * Created by koemdzhiev on 30/05/2015.
 */
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab2 extends Fragment {
    BluetoothSPP bt;

    TextView textStatus, textRead;
    EditText etMessage;
    AlertDialog.Builder builder;
    Button btnSend;

    Menu menu;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_2,container,false);
        textRead = (TextView)v.findViewById(R.id.textRead);
        textStatus = (TextView)v.findViewById(R.id.textStatus);
        etMessage = (EditText)v.findViewById(R.id.etMessage);
        btnSend = (Button)v.findViewById(R.id.btnSend);
        builder = new AlertDialog.Builder(getContext());


        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                textRead.append(message + "\n");
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceDisconnected() {
                final CharSequence[] items = {"다시연결하기"};


                builder.setTitle("연결이 끊겼습니다.")        // 제목 설정
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){
                            // 목록 클릭시 설정
                            public void onClick(DialogInterface dialog, int index){

                                bt.autoConnect("wonjungod");



                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기



            }

            public void onDeviceConnectionFailed() {
                textStatus.setText("Status : Connection failed");
            }

            public void onDeviceConnected(String name, String address) {
                textStatus.setText("Status : Connected to " + name);
            }
        });
        return v;
    }

    public void setup() {

        btnSend.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(etMessage.getText().length() != 0) {
                    bt.send(etMessage.getText().toString(), true);
                    etMessage.setText("");
                }
            }
        });
    }

}