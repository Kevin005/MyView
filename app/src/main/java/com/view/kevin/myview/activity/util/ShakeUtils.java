package com.view.kevin.myview.activity.util;

/**
 * Created by Kevin on 2017/4/28.
 */

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 摇一摇工具类
 * 使用说明：
 * 1、在需要使用摇一摇功能的Activity onCreate中实例化该工具类并设置摇一摇监听
 * <p>
 * 2、分别在Activity的onResume和onPause方法中调用该工具类的onResume和onPause方法
 * mShakeUtils.onResume();
 * mShakeUtils.onPause();
 */
public class ShakeUtils implements SensorEventListener {
    private static ShakeUtils _shakeUtils;
    private SensorManager mSensorManager = null;
    private OnShakeListener mOnShakeListener = null;
    private int sensor_value = Integer.MAX_VALUE;

    private ShakeUtils(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public static ShakeUtils getInstant(Context context) {
        if (_shakeUtils == null) {
            _shakeUtils = new ShakeUtils(context);
        }
        return _shakeUtils;
    }

    public enum EventType {SHAKE, KNOCKED}

    public void initEventType(EventType eventType) {
        if (eventType.equals(EventType.SHAKE)) {
            sensor_value = 15;//摇动阈值
        } else if (eventType.equals(EventType.KNOCKED)) {
            sensor_value = 100;//碰撞阈值
        }
    }

    public interface OnShakeListener {
        void onShake();
    }

    public void setOnShakeListener(OnShakeListener onShakeListener) {
        mOnShakeListener = onShakeListener;
    }

    public void onResume() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
            float[] values = event.values;
            //可以调节摇一摇的灵敏度
            if ((Math.abs(values[0]) > sensor_value || Math.abs(values[1]) > sensor_value || Math.abs(values[2]) > sensor_value)) {
                System.out.println("sensor value == " + " " + values[0] + " " + values[1] + " " + values[2]);
                if (mOnShakeListener != null) {
                    mOnShakeListener.onShake();
                }
            }
        }
    }
}
