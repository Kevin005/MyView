package com.view.kevin.myview.activity.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.view.kevin.myview.R;

public class BlinkActivity extends AppCompatActivity {

    private Button rotateButton = null;

    private Button scaleButton = null;

    private Button alphaButton = null;

    private Button translateButton = null;

    private ImageView image = null;
    private ImageView image2 = null;
    private LinearLayout line_line = null;

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_activity_blink);

        rotateButton = (Button) findViewById(R.id.rotateButton);

        scaleButton = (Button) findViewById(R.id.scaleButton);

        alphaButton = (Button) findViewById(R.id.alphaButton);

        translateButton = (Button) findViewById(R.id.translateButton);

        image = (ImageView) findViewById(R.id.image);
        image2 = (ImageView) findViewById(R.id.image2);
        line_line = (LinearLayout) findViewById(R.id.line_line);

        rotateButton.setOnClickListener(new RotateButtonListener());

        scaleButton.setOnClickListener(new ScaleButtonListener());

        alphaButton.setOnClickListener(new AlphaButtonListener());

        translateButton.setOnClickListener(

                new TranslateButtonListener());

    }

    class AlphaButtonListener implements View.OnClickListener {

        public void onClick(View v) {

            //创建一个AnimationSet对象，参数为Boolean型，

            //true表示使用Animation的interpolator，false则是使用自己的

            AnimationSet animationSet = new AnimationSet(true);

            //创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明

            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);

            //设置动画执行的时间

            alphaAnimation.setDuration(500);

            //将alphaAnimation对象添加到AnimationSet当中

            animationSet.addAnimation(alphaAnimation);

            //使用ImageView的startAnimation方法执行动画

            image.startAnimation(animationSet);

        }

    }

    class RotateButtonListener implements View.OnClickListener {

        public void onClick(View v) {

            AnimationSet animationSet = new AnimationSet(true);

            //参数1：从哪个旋转角度开始

            //参数2：转到什么角度

            //后4个参数用于设置围绕着旋转的圆的圆心在哪里

            //参数3：确定x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标

            //参数4：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴

            //参数5：确定y轴坐标的类型

            //参数6：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴

            RotateAnimation rotateAnimation = new RotateAnimation(0, 360,

                    Animation.RELATIVE_TO_SELF, 0.5f,

                    Animation.RELATIVE_TO_SELF, 0.5f);

            rotateAnimation.setDuration(1000);

            animationSet.addAnimation(rotateAnimation);

            image.startAnimation(animationSet);

        }

    }

    class ScaleButtonListener implements View.OnClickListener {

        public void onClick(View v) {

            AnimationSet animationSet = new AnimationSet(true);

            //参数1：x轴的初始值

            //参数2：x轴收缩后的值

            //参数3：y轴的初始值

            //参数4：y轴收缩后的值

            //参数5：确定x轴坐标的类型

            //参数6：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴

            //参数7：确定y轴坐标的类型

            //参数8：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴

//            ScaleAnimation scaleAnimation = new ScaleAnimation(
//
//                    0, 0.1f, 0, 0.1f,
//
//                    Animation.RELATIVE_TO_SELF, 0.5f,
//
//                    Animation.RELATIVE_TO_SELF, 0.5f);
//
//            scaleAnimation.setDuration(1000);
//
//            animationSet.addAnimation(scaleAnimation);
//
//            image.startAnimation(animationSet);


            /**
             * 设置缩放中心的值
             */
            int pivotX = image.getWidth();
            int pivotY = image.getHeight() / 2;

            //初始化缩放对象
            ScaleAnimation sa = new ScaleAnimation(1f, 1f, 0.8f, 0.2f, pivotX,
                    pivotY);
            //设置插值器，用于控制动画的行为，这里是控制动画的重复次数3
            sa.setInterpolator(new CycleInterpolator(2f));
            //动画的速率
            sa.setDuration(500);
            image.startAnimation(sa);


/**
 * 设置缩放中心的值
 */
            int pivotX2 = image2.getWidth();
            int pivotY2 = image2.getHeight() / 2;

            //初始化缩放对象
            ScaleAnimation sa2 = new ScaleAnimation(1f, 1f, 0.8f, 0.2f, pivotX2,
                    pivotY2);
            //设置插值器，用于控制动画的行为，这里是控制动画的重复次数3
            sa2.setInterpolator(new CycleInterpolator(2f));
            //动画的速率
            sa2.setDuration(500);
            image2.startAnimation(sa2);


        }

    }

    class TranslateButtonListener implements View.OnClickListener {

        public void onClick(View v) {

            AnimationSet animationSet = new AnimationSet(true);

            //参数1～2：x轴的开始位置

            //参数3～4：y轴的开始位置

            //参数5～6：x轴的结束位置

            //参数7～8：x轴的结束位置

            TranslateAnimation translateAnimation =

                    new TranslateAnimation(

                            Animation.RELATIVE_TO_SELF, 0f,

                            Animation.RELATIVE_TO_SELF, 0.5f,

                            Animation.RELATIVE_TO_SELF, 0f,

                            Animation.RELATIVE_TO_SELF, 0.5f);

            translateAnimation.setDuration(1000);

            animationSet.addAnimation(translateAnimation);

//            image.startAnimation(animationSet);
//            image.startAnimation(animationSet);
            line_line.startAnimation(animationSet);
        }

    }

}
