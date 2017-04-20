package com.view.kevin.myview.activity.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.view.kevin.myview.R;
import com.view.kevin.myview.activity.view.LottieAnimView2;

public class MainActivity extends Activity {
    public static boolean isblow;
    private LottieAnimView2 lottieView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                Log.e("TAG", "YES");
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        lottieView = (LottieAnimView2) findViewById(R.id.lottie_view);
//
////        lottieView.setAnimation("cat_level_1.json", LottieAnimationView.CacheStrategy.None);
////        lottieView.setImageAssetsFolder("images/");
////        lottieView.loop(true);
////        lottieView.playAnimation();
//        lottieView.intentCurrentDefaultAction(lottieView.ACTION_TYPE_DEFAULT);
//
//        Button btn_action1 = (Button) findViewById(R.id.btn_action1);
//        btn_action1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lottieView.intentBlockAction(lottieView.ACTION_TYPE_BLOCK_SMILE, null);
//            }
//        });
//        Button btn_action2 = (Button) findViewById(R.id.btn_action2);
//        btn_action2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lottieView.intentBlockAction(lottieView.ACTION_TYPE_BLOCK_EAT_1_1, null);
//            }
//        });
//        Button btn_action3 = (Button) findViewById(R.id.btn_action3);
//        btn_action3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lottieView.intentBlockAction(lottieView.ACTION_TYPE_BLOCK_GET_DOWN, null);
//            }
//        });
//        Button btn_action4 = (Button) findViewById(R.id.btn_action4);
//        btn_action4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lottieView.intentBlockAction(lottieView.ACTION_TYPE_BLOCK_BARK, null);
//            }
//        });
    }
}

