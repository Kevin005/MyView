package com.view.kevin.myview.activity.view.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.view.kevin.myview.R;
import com.view.kevin.myview.activity.view.LottieAnimView;

public class LottieAnimationActivity extends Activity {
    public final String TAG = LottieAnimationActivity.class.getName();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_animation);
        final LottieAnimView lottie_all = (LottieAnimView) findViewById(R.id.lottie_all);
        lottie_all.intentCurrentDefaultAction(LottieAnimView.ACTION_TYPE_DEFAULT_GET_HUNGER_1);

        Button btn_action = (Button) findViewById(R.id.btn_action);
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottie_all.intentBlockAction(LottieAnimView.ACTION_TYPE_BLOCK_EAT_1_1);
            }
        });


        lottie_all.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e(TAG, "+++++++++" + animation.getAnimatedValue().toString());
            }
        });

    }

//        lottie_all.setAnimation("cw_quanti_v2.json");
//        lottie_all.setImageAssetsFolder("images/");
//        lottie_all.playAnimation();
//        lottie_all.loop(true);
}
