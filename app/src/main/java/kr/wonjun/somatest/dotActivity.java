package kr.wonjun.somatest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import kr.edcan.dispersionchart.DispersionChartView;
import kr.edcan.dispersionchart.Dot;

public class dotActivity extends AppCompatActivity {
    BluetoothSPP bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot);
        bt=new BluetoothSPP(this);
        DispersionChartView dpView = (DispersionChartView) findViewById(R.id.dpChartView);
.

        dpView.addDot(new Dot(50.001f, 50f));
        dpView.addDot(new Dot(50.002f, 50f));
        dpView.addDot(new Dot(50.003f, 50f));
        dpView.addDot(new Dot(50.004f, 50f));
        dpView.addDot(new Dot(50.005f, 50f));
        dpView.addDot(new Dot(50.006f, 50f));
        dpView.addDot(new Dot(50.007f, 50f));
        dpView.addDot(new Dot(50.008f, 50f));
        dpView.addDot(new Dot(50.009f, 50f));
        dpView.addDot(new Dot(50.010f, 50f));

        dpView.addDot(new Dot(50.021f, 50f));
        dpView.addDot(new Dot(50.022f, 50f));
        dpView.addDot(new Dot(50.023f, 50f));
        dpView.addDot(new Dot(50.024f, 50f));
        dpView.addDot(new Dot(50.025f, 50f));
        dpView.addDot(new Dot(50.026f, 50f));
        dpView.addDot(new Dot(50.027f, 50f));
        dpView.addDot(new Dot(50.028f, 50f));
        dpView.addDot(new Dot(50.029f, 50f));
        dpView.addDot(new Dot(50.020f, 50f));

        dpView.addDot(new Dot(50.031f, 50f));
        dpView.addDot(new Dot(50.032f, 50f));
        dpView.addDot(new Dot(50.033f, 50f));
        dpView.addDot(new Dot(50.034f, 50f));
        dpView.addDot(new Dot(50.035f, 50f));
        dpView.addDot(new Dot(50.036f, 50f));
        dpView.addDot(new Dot(50.037f, 50f));
        dpView.addDot(new Dot(50.038f, 50f));
        dpView.addDot(new Dot(50.039f, 50f));
        dpView.addDot(new Dot(50.030f, 50f));

        dpView.addDot(new Dot(50.041f, 50f));
        dpView.addDot(new Dot(50.042f, 50f));
        dpView.addDot(new Dot(50.043f, 50f));
        dpView.addDot(new Dot(50.044f, 50f));
        dpView.addDot(new Dot(50.045f, 50f));
        dpView.addDot(new Dot(50.046f, 50f));
        dpView.addDot(new Dot(50.047f, 50f));
        dpView.addDot(new Dot(50.048f, 50f));
        dpView.addDot(new Dot(50.049f, 50f));
        dpView.addDot(new Dot(50.040f, 50f));

        dpView.addDot(new Dot(50.051f, 50f));
        dpView.addDot(new Dot(50.052f, 50f));
        dpView.addDot(new Dot(50.053f, 50f));
        dpView.addDot(new Dot(50.054f, 50f));
        dpView.addDot(new Dot(50.055f, 50f));
        dpView.addDot(new Dot(50.056f, 50f));
        dpView.addDot(new Dot(50.057f, 50f));
        dpView.addDot(new Dot(50.058f, 50f));
        dpView.addDot(new Dot(50.059f, 50f));
        dpView.addDot(new Dot(50.050f, 50f));

        dpView.addDot(new Dot(50.061f, 50f));
        dpView.addDot(new Dot(50.062f, 50f));
        dpView.addDot(new Dot(50.063f, 50f));
        dpView.addDot(new Dot(50.064f, 50f));
        dpView.addDot(new Dot(50.065f, 50f));
        dpView.addDot(new Dot(50.066f, 50f));
        dpView.addDot(new Dot(50.067f, 50f));
        dpView.addDot(new Dot(50.068f, 50f));
        dpView.addDot(new Dot(50.069f, 50f));
        dpView.addDot(new Dot(50.060f, 50f));

        dpView.addDot(new Dot(50.071f, 50f));
        dpView.addDot(new Dot(50.072f, 50f));
        dpView.addDot(new Dot(50.073f, 50f));
        dpView.addDot(new Dot(50.074f, 50f));
        dpView.addDot(new Dot(50.075f, 50f));
        dpView.addDot(new Dot(50.076f, 50f));
        dpView.addDot(new Dot(50.077f, 50f));
        dpView.addDot(new Dot(50.078f, 50f));
        dpView.addDot(new Dot(50.079f, 50f));

        dpView.addDot(new Dot(50.551f, 50f));
        dpView.addDot(new Dot(50.552f, 50f));
        dpView.addDot(new Dot(50.553f, 50f));
        dpView.addDot(new Dot(50.554f, 50f));
        dpView.addDot(new Dot(50.555f, 50f));
        dpView.addDot(new Dot(50.556f, 50f));
        dpView.addDot(new Dot(50.557f, 50f));
        dpView.addDot(new Dot(50.558f, 50f));
        dpView.addDot(new Dot(50.559f, 50f));
        dpView.addDot(new Dot(50.550f, 50f));
        dpView.addDot(new Dot(50.550f, 50f));






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
}
