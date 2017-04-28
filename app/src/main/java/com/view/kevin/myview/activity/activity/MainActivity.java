package com.view.kevin.myview.activity.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.view.kevin.myview.R;
import com.view.kevin.myview.activity.util.ShakeUtils;

public class MainActivity extends Activity {
    private ShakeUtils mShakeUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShakeUtils = ShakeUtils.getInstant(this);
        mShakeUtils.initEventType(ShakeUtils.EventType.SHAKE);
        mShakeUtils.setOnShakeListener(new ShakeUtils.OnShakeListener() {
                                           @Override
                                           public void onShake() {
                                               Toast.makeText(MainActivity.this, "摇一摇", Toast.LENGTH_SHORT).show();
                                           }
                                       }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShakeUtils.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShakeUtils.onPause();
    }


}

