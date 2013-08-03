package com.ztemt.test.advance.sensor;

import java.text.DecimalFormat;

import android.app.Fragment;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.ztemt.test.advance.MonitorApp;
import com.ztemt.test.advance.R;

public class SensorActivity extends Fragment {

    private SensorListView mListView;
    private MenuItem mUpdateUIMenuItem;

    private WindowManager mWindowManager;
    private LayoutParams mFloatViewParams;

    private FloatView mFloatView;
    private DecimalFormat mFormater = new DecimalFormat("#.##");
    private long mTimestamp = 0;

    private static SensorActivity sInstance;

    static SensorActivity getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sInstance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (mListView == null) {
            mListView = new SensorListView(getActivity());
        }

        setHasOptionsMenu(true);

        return mListView;
    }

    @Override
    public void onStart() {
        super.onStart();
        removeFloatView();
    }

    @Override
    public void onStop() {
        super.onStop();
        createFloatView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeFloatView();
        sInstance = null;
    }

    /**
     * Load the options menu (defined in xml)
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.streaming_menu, menu);

        mUpdateUIMenuItem = menu.findItem(R.id.ui_data_update_toggle);
        mUpdateUIMenuItem.setTitle(SensorStreamEventListener.sDisableUIDataUpdate
                ? "Enable Screen Update" : "Disable Screen Update");
    }

    /**
     * Defines what occurs when the user selects one of the menu options.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.all_listeners_add:
            new AllSensorDialogClickListener(getActivity());
            return true;
        case R.id.all_listeners_remove:
            SensorClickListener.clearAllListeners(getActivity());
            return true;
        case R.id.ui_data_update_toggle:
            SensorStreamEventListener.sDisableUIDataUpdate = !SensorStreamEventListener.sDisableUIDataUpdate;
            mUpdateUIMenuItem.setTitle(SensorStreamEventListener.sDisableUIDataUpdate
                    ? "Enable Screen Update" : "Disable Screen Update");
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("deprecation")
    public void onSensorChanged(SensorListItem listItem) {
        if (mFloatView == null || System.currentTimeMillis() - mTimestamp < 100) return;

        mTimestamp = System.currentTimeMillis();

        String type = listItem.getSensorType();
        String value = SensorUtilityFunctions.streamRateString(listItem.getStreamRate())
                + "(" + mFormater.format(SensorUtilityFunctions.effectiveStreamRate(listItem)) + ")";
        String text = type + ": " + value + "\n";

        switch (listItem.getSensor().getType()) {
        case Sensor.TYPE_ACCELEROMETER:
            mFloatView.setAccelText(text);
            break;
        case Sensor.TYPE_GYROSCOPE:
            mFloatView.setGyroText(text);
            break;
        case Sensor.TYPE_LIGHT:
            mFloatView.setLightText(text);
            break;
        case Sensor.TYPE_MAGNETIC_FIELD:
            mFloatView.setMagText(text);
            break;
        case Sensor.TYPE_ORIENTATION:
            mFloatView.setOriText(text);
            break;
        case Sensor.TYPE_PRESSURE:
            mFloatView.setPressText(text);
            break;
        case Sensor.TYPE_PROXIMITY:
            mFloatView.setProxText(text);
            break;
        case Sensor.TYPE_TEMPERATURE:
            mFloatView.setTempText(text);
            break;
        case Sensor.TYPE_LINEAR_ACCELERATION:
            mFloatView.setLaccelText(text);
            break;
        case Sensor.TYPE_GRAVITY:
            mFloatView.setGravityText(text);
            break;
        case Sensor.TYPE_ROTATION_VECTOR:
            mFloatView.setRotvecText(text);
            break;
        }
    }

    private void createFloatView() {
        mFloatView = new FloatView(getActivity().getApplicationContext());

        mWindowManager = (WindowManager) getActivity().getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        mFloatViewParams = ((MonitorApp) getActivity().getApplication())
                .getFloatViewParams();
        mFloatViewParams.type = LayoutParams.TYPE_PHONE;
        mFloatViewParams.format = PixelFormat.RGBA_8888;
        mFloatViewParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                | LayoutParams.FLAG_NOT_FOCUSABLE;
        mFloatViewParams.gravity = Gravity.LEFT | Gravity.TOP;
        mFloatViewParams.x = 0;
        mFloatViewParams.y = 0;
        mFloatViewParams.width = LayoutParams.WRAP_CONTENT;
        mFloatViewParams.height = LayoutParams.WRAP_CONTENT;

        mWindowManager.addView(mFloatView, mFloatViewParams);
    }

    private void removeFloatView() {
        if (mWindowManager != null) {
            mWindowManager.removeView(mFloatView);
        }
    }
}
