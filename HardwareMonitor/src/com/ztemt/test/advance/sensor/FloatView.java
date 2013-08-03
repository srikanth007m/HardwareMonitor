package com.ztemt.test.advance.sensor;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ztemt.test.advance.MonitorApp;

public class FloatView extends LinearLayout {

    private float mTouchX;
    private float mTouchY;
    private float mX;
    private float mY;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mFloatViewParams;

    private TextView mAccelView;
    private TextView mGyroView;
    private TextView mLightView;
    private TextView mMagView;
    private TextView mOriView;
    private TextView mPressView;
    private TextView mProxView;
    private TextView mTempView;
    private TextView mLaccelView;
    private TextView mGravityView;
    private TextView mRotvecView;

    public FloatView(Context context) {
        super(context);

        mWindowManager = (WindowManager) getContext().getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        mFloatViewParams = ((MonitorApp) getContext().getApplicationContext())
                .getFloatViewParams();

        mAccelView = new TextView(context);
        mAccelView.setVisibility(View.GONE);
        mGyroView = new TextView(context);
        mGyroView.setVisibility(View.GONE);
        mLightView = new TextView(context);
        mLightView.setVisibility(View.GONE);
        mMagView = new TextView(context);
        mMagView.setVisibility(View.GONE);
        mOriView = new TextView(context);
        mOriView.setVisibility(View.GONE);
        mPressView = new TextView(context);
        mPressView.setVisibility(View.GONE);
        mProxView = new TextView(context);
        mProxView.setVisibility(View.GONE);
        mTempView = new TextView(context);
        mTempView.setVisibility(View.GONE);
        mLaccelView = new TextView(context);
        mLaccelView.setVisibility(View.GONE);
        mGravityView = new TextView(context);
        mGravityView.setVisibility(View.GONE);
        mRotvecView = new TextView(context);
        mRotvecView.setVisibility(View.GONE);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        setOrientation(LinearLayout.VERTICAL);

        addView(mAccelView, params);
        addView(mGyroView, params);
        addView(mLightView, params);
        addView(mMagView, params);
        addView(mOriView, params);
        addView(mPressView, params);
        addView(mProxView, params);
        addView(mTempView, params);
        addView(mLaccelView, params);
        addView(mGravityView, params);
        addView(mRotvecView, params);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mX = event.getRawX();
        mY = event.getRawY() - 25;  // status bar height is 25

        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            mTouchX = event.getX();
            mTouchY = event.getY();
            break;
        case MotionEvent.ACTION_MOVE:
            updateViewPosition();
            break;
        case MotionEvent.ACTION_UP:
            updateViewPosition();
            mTouchX = 0;
            mTouchY = 0;
            break;
        }
        return true;
    }

    public void setAccelText(String text) {
        mAccelView.setText(text);
        mAccelView.setVisibility(View.VISIBLE);
    }

    public void setGyroText(String text) {
        mGyroView.setText(text);
        mGyroView.setVisibility(View.VISIBLE);
    }

    public void setLightText(String text) {
        mLightView.setText(text);
        mLightView.setVisibility(View.VISIBLE);
    }

    public void setMagText(String text) {
        mMagView.setText(text);
        mMagView.setVisibility(View.VISIBLE);
    }

    public void setOriText(String text) {
        mOriView.setText(text);
        mOriView.setVisibility(View.VISIBLE);
    }

    public void setPressText(String text) {
        mPressView.setText(text);
        mPressView.setVisibility(View.VISIBLE);
    }

    public void setProxText(String text) {
        mProxView.setText(text);
        mProxView.setVisibility(View.VISIBLE);
    }

    public void setTempText(String text) {
        mTempView.setText(text);
        mTempView.setVisibility(View.VISIBLE);
    }

    public void setLaccelText(String text) {
        mLaccelView.setText(text);
        mLaccelView.setVisibility(View.VISIBLE);
    }

    public void setGravityText(String text) {
        mGravityView.setText(text);
        mGravityView.setVisibility(View.VISIBLE);
    }

    public void setRotvecText(String text) {
        mRotvecView.setText(text);
        mRotvecView.setVisibility(View.VISIBLE);
    }

    private void updateViewPosition() {
        mFloatViewParams.x = (int) (mX - mTouchX);
        mFloatViewParams.y = (int) (mY - mTouchY);
        mWindowManager.updateViewLayout(this, mFloatViewParams);
    }
}
