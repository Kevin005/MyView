package com.view.kevin.myview.activity.view.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.view.kevin.myview.R;
import com.view.kevin.myview.activity.view.LottieAnimView;

public class LottieAnimationActivity extends Activity implements View.OnClickListener {
    public final String TAG = LottieAnimationActivity.class.getName();
    private LottieAnimView lottie_all;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_animation);
        lottie_all = (LottieAnimView) findViewById(R.id.lottie_all);
        lottie_all.intentCurrentDefaultAction(LottieAnimView.ACTION_TYPE_DEFAULT);
//        lottie_all.intentCurrentDefaultAction(LottieAnimView.ACTION_TYPE_DEFAULT_GET_HUNGER_1);
//        lottie_all.intentCurrentDefaultAction(LottieAnimView.ACTION_TYPE_DEFAULT_SIT_DOWN);
        findViewById(R.id.btn_action1).setOnClickListener(this);
        findViewById(R.id.btn_action2).setOnClickListener(this);
        findViewById(R.id.btn_action3).setOnClickListener(this);
        findViewById(R.id.btn_action4).setOnClickListener(this);
        findViewById(R.id.btn_action5).setOnClickListener(this);
        findViewById(R.id.btn_action6).setOnClickListener(this);
//        Button btn_action = (Button) findViewById(R.id.btn_action);
//        btn_action.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lottie_all.intentBlockAction(LottieAnimView.ACTION_TYPE_BLOCK_EAT_1_1);
//            }
//        });
//
//
//        lottie_all.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Log.e(TAG, "+++++++++" + animation.getAnimatedValue().toString());
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_action1:
                lottie_all.intentBlockAction(LottieAnimView.ACTION_TYPE_BLOCK_SHOWER, null);
                break;
            case R.id.btn_action2:
                lottie_all.intentBlockAction(LottieAnimView.ACTION_TYPE_BLOCK_PLAY_BALL, null);
                break;
            case R.id.btn_action3:
                lottie_all.intentBlockAction(LottieAnimView.ACTION_TYPE_BLOCK_SMILE, null);
                break;
            case R.id.btn_action4:
                lottie_all.intentBlockAction(LottieAnimView.ACTION_TYPE_BLOCK_EAT_1_1, null);
                break;
            case R.id.btn_action5:
                lottie_all.intentBlockAction(LottieAnimView.ACTION_TYPE_BLOCK_BARK, null);
                break;
            case R.id.btn_action6:
                lottie_all.intentBlockAction(LottieAnimView.ACTION_TYPE_BLOCK_GET_DOWN, null);
                break;
        }
    }
}
