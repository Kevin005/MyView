package com.view.kevin.myview.activity.view.activity;

import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.view.kevin.myview.R;

public class SideBarActivity extends AppCompatActivity {

    private View menu;
    private final static int SHOW_MENU = 1;
    private final static int HIDE_MENU = -1;
    private int swipe_tag = SHOW_MENU;
    private int max_menu_margin = 0;
    private int min_menu_margin;
    private float beginX;
    private float latestX;
    private float diffX;
    private float latestMargin;

    private FrameLayout.LayoutParams lp;

    /**
     * 实现了侧边栏
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.view_activity_sidebar);

        menu = findViewById(R.id.menu);
        lp = (FrameLayout.LayoutParams) menu.getLayoutParams();
        min_menu_margin = lp.leftMargin;

        menu.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //获取一个活动的MotionEvent,被设计为多点操作
                int action = MotionEventCompat.getActionMasked(event);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        beginX = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        latestX = event.getX();
                        diffX = latestX - beginX;
                        swipe_tag = diffX > 0 ? SHOW_MENU : HIDE_MENU;
                        latestMargin = lp.leftMargin + diffX;

                        if (latestMargin > min_menu_margin
                                && latestMargin < max_menu_margin) {
                            lp.leftMargin = (int) (latestMargin);
                            menu.setLayoutParams(lp);
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        TranslateAnimation tAnim;
                        if (swipe_tag == SHOW_MENU) {
                            tAnim = new TranslateAnimation(0, max_menu_margin
                                    - latestMargin, 0, 0);
                            tAnim.setInterpolator(new DecelerateInterpolator());
                            tAnim.setDuration(800);
                            menu.startAnimation(tAnim);
                        } else {
                            tAnim = new TranslateAnimation(0, min_menu_margin
                                    - latestMargin, 0, 0);
                            tAnim.setDuration(800);
                            menu.startAnimation(tAnim);
                        }
                        //在动画结束的时刻，移动menu的位置，使menu真正移动。
                        tAnim.setAnimationListener(new Animation.AnimationListener() {

                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (swipe_tag == SHOW_MENU) {
                                    lp.leftMargin = max_menu_margin;
                                    menu.setLayoutParams(lp);
                                } else {
                                    lp.leftMargin = min_menu_margin;
                                    menu.setLayoutParams(lp);
                                }
                                menu.clearAnimation();
                            }
                        });
                        break;
                }
                return true;
            }
        });
    }

}

