package kr.wonjun.somatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.edcan.dispersionchart.DispersionChartView;
import kr.edcan.dispersionchart.Dot;

public class dotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot);

        DispersionChartView dpView =(DispersionChartView) findViewById(R.id.dpChartView);

        dpView.addDot(new Dot(10f,30f));
        dpView.addDot(new Dot(15f,30f));
        dpView.addDot(new Dot(16f,3f));
        dpView.addDot(new Dot(17f,30f));
        dpView.addDot(new Dot(19f,30f));
        dpView.addDot(new Dot(50f,50f));







    }
}
