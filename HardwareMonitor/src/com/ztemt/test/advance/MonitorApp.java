package com.ztemt.test.advance;

import android.app.Application;
import android.view.WindowManager.LayoutParams;

public class MonitorApp extends Application {

    private LayoutParams mParams = new LayoutParams();

    public LayoutParams getFloatViewParams() {
        return mParams;
    }
}
