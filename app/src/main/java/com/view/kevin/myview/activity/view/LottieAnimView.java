package com.view.kevin.myview.activity.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;

import com.airbnb.lottie.LottieAnimationView;

import static java.lang.Thread.sleep;

/**
 * Created by Kevin on 2017/3/28.
 */

public class LottieAnimView extends LottieAnimationView {
    private String[] walk = new String[]{"walk", "0", "0.155963", "6233"};//走路
    private String[] sit_down = new String[]{"sit_down", "0.156797", "0.173478", "666"};//坐下
    private String[] angry = new String[]{"angry", "0.174312", "0.220183", "1833"};//生气
    private String[] smile = new String[]{"smile", "0.246872", "0.3211", "2966"};//微笑
    private String[] hunger_1 = new String[]{"hunger_1", "0.321934", "0.341951", "799"};//饥饿，扑倒
    private String[] hunger_2 = new String[]{"hunger_2", "0.342785", "0.47623", "5333"};//饥饿，倒下循环
    private String[] eat_1_1 = new String[]{"eat_1_1", "0.477064", "0.612176", "5400"};//吃饭，开心
    private String[] eat_1_2 = new String[]{"eat_1_2", "0.61301", "0.706422", "3733"};//吃饭，开心
    private String[] eat_1_call = new String[]{"eat_1_call", "0.843202", "0.90909", "2633"};//吃饭，吃完叫和单独叫共用
    private String[] get_down = new String[]{"get_down", "0.920767", "1.0", "3166"};//趴下

    private ValueAnimator default_valueAnimator;

    private int default_current_action_count = 0;//默认动作组当前动作的次数
    private String default_current_action = "";//默认动作组的当前动作
    private String default_block_current_action = "";//中断组的当前动作

    private boolean is_current_block_state = false;

    public static final int ACTION_TYPE_DEFAULT = 1;//动作组，默认指令，三个动作
    public static final int ACTION_TYPE_DEFAULT_GET_HUNGER_1 = 2;//动作组，默认指令，饥饿趴着
    public static final int ACTION_TYPE_BLOCK_EAT_1_1 = 3;//动作组，中断指令，趴着

    public LottieAnimView(Context context) {
        super(context);
        init();
    }

    public LottieAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LottieAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        setAnimation("cw_quanti_v3.json");
//        setImageAssetsFolder("zoulu/");
    }

    //中断指令动作
    public void intentBlockAction(int action_type) {
        switch (action_type) {
            case ACTION_TYPE_BLOCK_EAT_1_1:
                setBlockAnimation(eat_1_1);
                break;
        }
    }

    //默认指令动作
    public void intentCurrentDefaultAction(int action_type) {
        switch (action_type) {
            case ACTION_TYPE_DEFAULT:
                setCurrentDefaultAnimation(walk);
                break;
            case ACTION_TYPE_DEFAULT_GET_HUNGER_1:
                setCurrentDefaultAnimation(hunger_1);
                break;
        }
    }
    //动作组，默认三个
    private void setCurrentDefaultAnimation(String[] strs) {
        is_current_block_state = false;
        int repeat_count = 0;
        String current_name = strs[0];
        if (current_name.equals("hunger_2")) {
            repeat_count = -1;
        }
        float start_time = Float.valueOf(strs[1]);
        float end_time = Float.valueOf(strs[2]);
        long duration = Long.valueOf(strs[3]);
        default_current_action = current_name;
        default_valueAnimator = ValueAnimator.ofFloat(start_time, end_time).setDuration(duration);
        default_valueAnimator.setRepeatCount(repeat_count);
        default_valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                    @Override
                                                    public void onAnimationUpdate(ValueAnimator animation) {
                                                        setProgress((float) animation.getAnimatedValue());
                                                    }
                                                }
        );
        default_valueAnimator.start();
        default_valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!is_current_block_state) {
                    if (default_current_action.equals("walk")) {
                        setCurrentDefaultAnimation(sit_down);
                    } else if (default_current_action.equals("sit_down")) {
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        setCurrentDefaultAnimation(get_down);
                    } else if (default_current_action.equals("get_down")) {
                        setCurrentDefaultAnimation(walk);
                    } else if (default_current_action.equals("hunger_1")) {
                        setCurrentDefaultAnimation(hunger_2);
                    } else if (default_current_action.equals("hunger_2")) {
                        setCurrentDefaultAnimation(hunger_2);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                is_current_block_state = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    private void setBlockAnimation(String[] strs) {
        if (default_valueAnimator.isRunning()) {
            default_valueAnimator.cancel();
        }
//        setAnimation("cw_quanti_v2.json");
//        setImageAssetsFolder("images/");
        String current_name = strs[0];
        if (current_name.equals("eat_1_1")) {
            default_block_current_action = "eat_1_1";
        } else if (current_name.equals("eat_1_2")) {
            default_block_current_action = "eat_1_2";
        }
        float start_time = Float.valueOf(strs[1]);
        float end_time = Float.valueOf(strs[2]);
        long duration = Long.valueOf(strs[3]);
        ValueAnimator block_valueAnimator = ValueAnimator.ofFloat(start_time, end_time).setDuration(duration);
        block_valueAnimator.setRepeatCount(0);
        block_valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                  @Override
                                                  public void onAnimationUpdate(ValueAnimator animation) {
                                                      setProgress((float) animation.getAnimatedValue());
                                                  }
                                              }
        );
        block_valueAnimator.start();
        block_valueAnimator.addListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {
                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                //执行完中断指令后继续执行默认指令
                                                blockGroupEnd();
                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animation) {
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animation) {
                                            }
                                        }

        );
    }

    private void blockGroupEnd() {
        if (default_block_current_action.equals("eat_1_1")) {//中断组，eat_1优先级最高
            setBlockAnimation(eat_1_2);
        } else if (default_block_current_action.equals("eat_1_2")) {
            defaultGroupEnd();//中断组执行完毕，开始默认组
        }
    }

    private void defaultGroupEnd() {
        if (default_current_action.equals("walk")) {//默认组，优先级按循序排即可
            setCurrentDefaultAnimation(sit_down);
        } else if (default_current_action.equals("sit_down")) {
            setCurrentDefaultAnimation(get_down);
        } else if (default_current_action.equals("get_down")) {
            setCurrentDefaultAnimation(walk);
        } else if (default_current_action.equals("hunger_1") || default_current_action.equals("hunger_2")) {
            setCurrentDefaultAnimation(hunger_2);
        } else {
            setCurrentDefaultAnimation(walk);
        }
    }
}
