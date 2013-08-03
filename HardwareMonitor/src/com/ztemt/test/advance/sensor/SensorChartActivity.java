package com.ztemt.test.advance.sensor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ztemt.test.advance.R;
import com.ztemt.test.advance.chart.AxisLineChart;

public class SensorChartActivity extends Activity {

    private AxisLineChart mChart;
    private String mType;
    private boolean mPaused = false;
    private boolean mDisableUIDataUpdate;

    private static SensorChartActivity sInstance;

    static SensorChartActivity getInstance() {
        return sInstance;
    }

    public void onSensorChanged(SensorListItem listItem) {
        if (!mPaused && listItem.getSensorType().equals(mType)) {
            mChart.update(listItem.getData());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = getIntent().getStringExtra("title");
        String unit = getIntent().getStringExtra("unit");
        double maxX = getIntent().getDoubleExtra("max_x", 2000.0);
        double minY = getIntent().getDoubleExtra("min_y", 0.0);
        double maxY = getIntent().getDoubleExtra("max_y", 50.0);
        mType = getIntent().getStringExtra("type");

        mDisableUIDataUpdate = SensorStreamEventListener.sDisableUIDataUpdate;
        mChart = new AxisLineChart(this, title, unit, maxX, minY, maxY, 3);
        setContentView(mChart.getView());

        sInstance = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SensorStreamEventListener.sDisableUIDataUpdate = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        SensorStreamEventListener.sDisableUIDataUpdate = mDisableUIDataUpdate;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sInstance = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chart_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.chart_data_update_toggle:
            mPaused = !mPaused;
            item.setTitle(mPaused ? "Enable Chart Update" : "Disable Chart Update");
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        new Thread() {
            public void run() {
                mPaused = true;
            }
        }.start();
        super.onBackPressed();
    }
}
