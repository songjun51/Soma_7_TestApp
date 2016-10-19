package kr.wonjun.somatest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import kr.edcan.dispersionchart.DispersionChartView;
import kr.edcan.dispersionchart.Dot;

public class dotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot);

        DispersionChartView dpView = (DispersionChartView) findViewById(R.id.dpChartView);

        dpView.addDot(new Dot(10.1401f, 30f));
        dpView.addDot(new Dot(15f, 30f));
        dpView.addDot(new Dot(16f, 3f));
        dpView.addDot(new Dot(17f, 30f));
        dpView.addDot(new Dot(19f, 30f));
        dpView.addDot(new Dot(50f, 50f));
        dpView.addDot(new Dot(19f, 30f));1
        dpView.addDot(new Dot(50f, 50f));
        dpView.addDot(new Dot(19f, 30f));
        dpView.addDot(new Dot(50f, 50f));
        dpView.addDot(new Dot(19f, 30f));
        dpView.addDot(new Dot(50f, 50f));
        dpView.addDot(new Dot(19f, 30f));
        dpView.addDot(new Dot(50f, 50f));
        dpView.addDot(new Dot(19f, 30f));
        dpView.addDot(new Dot(50f, 50f));
        dpView.addDot(new Dot(19f, 30f));
        dpView.addDot(new Dot(50f, 50f));
        dpView.addDot(new Dot(19f, 30f));
        dpView.addDot(new Dot(50f, 50f));


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
