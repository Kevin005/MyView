package com.view.kevin.myview.activity.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;

import static java.lang.Thread.sleep;

/**
 * Created by Kevin on 2017/3/28.
 * 适用于宠物主页面动效
 */

public class LottieAnimView2 extends LottieAnimationView implements Handler.Callback {
    private Handler command_handler;
    private BlockActionCallBack blockActionCallBack;//中断指令的回调
    private final int HANDLER_COMMAND_1 = 1;//中断指令吃
    private final int HANDLER_COMMAND_2 = 2;//中断指令坐下
    private final int HANDLER_COMMAND_3 = 3;//中断指令趴下
    private final int HANDLER_COMMAND_7 = 7;//中断指令叫
    private final int HANDLER_COMMAND_8 = 8;//中断指令笑
    private final int HANDLER_COMMAND_4 = 4;//默认指令，走路(三个动作)
    private final int HANDLER_COMMAND_5 = 5;//默认指令，饿
    private final int HANDLER_COMMAND_6 = 6;//默认指令，坐着

    private String[] walk = new String[5];//走路
    private String[] sit_down = new String[5];//坐下
    private String[] keep_sit_down = new String[5];//一直坐着
    private String[] angry = new String[5];//生气
    private String[] smile = new String[5];//微笑
    private String[] hunger = new String[5];//饥饿，倒下循环
    private String[] hunger_1 = new String[5];//饥饿，扑倒
    private String[] hunger_2 = new String[5];//饥饿，倒下循环
    private String[] eat_1_1 = new String[5];//吃饭，开始
    private String[] eat_1_2 = new String[5];//吃饭，开心摇头
    private String[] eat_1_dark = new String[5];//吃饭，吃完叫和单独叫共用
    private String[] get_down = new String[5];//趴下
    //狗一级
    private String[] dog_level_1_walk = new String[]{"walk", "0", "0.156842105", "5960", "1"};//走路
    private String[] dog_level_1_sit_down = new String[]{"sit_down", "0.225263157", "0.230526315", "200", "1"};//坐下
    private String[] dog_level_1_keep_sit_down = new String[]{"keep_sit_down", "0.225263157", "0.230526315", "200", "0"};//一直坐着
    private String[] dog_level_1_angry = new String[]{"angry", "0.157894736", "0.224210526", "2520", "0"};//生气
    private String[] dog_level_1_smile = new String[]{"smile", "0.231578947", "0.28", "1840", "0"};//微笑
    private String[] dog_level_1_hunger_1 = new String[]{"hunger_1", "0.281052631", "0.298947368", "680", "0"};//饥饿，扑倒
    private String[] dog_level_1_hunger_2 = new String[]{"hunger_2", "0.3", "0.442105263", "5400", "-1"};//饥饿，倒下循环
    private String[] dog_level_1_eat_1_1 = new String[]{"eat_1_1", "0.44315794", "0.583157894", "5320", "0"};//吃饭，开始
    private String[] dog_level_1_eat_1_2 = new String[]{"eat_1_2", "0.584210526", "0.686315789", "3880", "0"};//吃饭，开心摇头
    private String[] dog_level_1_eat_1_dark = new String[]{"eat_1_dark", "0.828421052", "0.910526315", "3120", "0"};//吃饭，吃完叫和单独叫共用
    private String[] dog_level_1_get_down = new String[]{"get_down", "0.911578947", "1.0", "3360", "0"};//趴下
    //猫一级
    private String[] cat_level_1_walk = new String[]{"walk", "0", "0.148662041", "6000", "1"};//走路
    private String[] cat_level_1_sit_down = new String[]{"sit_down", "0.683845391", "0.689791873", "240", "1"};//坐下
    private String[] cat_level_1_keep_sit_down = new String[]{"keep_sit_down", "0.683845391", "0.689791873", "240", "0"};//一直坐着
    private String[] cat_level_1_angry = new String[]{"angry", "0.149653121", "0.298315163", "6040", "0"};//生气
    private String[] cat_level_1_smile = new String[]{"smile", "0.299306243", "0.398414271", "4000", "0"};//微笑
    private String[] cat_level_1_hunger_1 = new String[]{"hunger", "0.549058473", "0.682854311", "5400", "0"};//饥饿，扑倒循环
    private String[] cat_level_1_eat_1_1 = new String[]{"eat_1_1", "0.766105054", "0.899900891", "5400", "0"};//吃饭，开始
    private String[] cat_level_1_eat_1_2 = new String[]{"eat_1_2", "0.900891972", "1.0", "4000", "0"};//吃饭，开心摇头
    private String[] cat_level_1_eat_1_dark = new String[]{"eat_1_dark", "0.690782953", "0.765113974", "3000", "0"};//吃饭，吃完叫和单独叫共用
    private String[] cat_level_1_get_down = new String[]{"get_down", "0.399405351", "0.548067393", "6000", "0"};//趴下

    private ValueAnimator default_valueAnimator;
    private ValueAnimator block_valueAnimator;

    private String default_current_action = "";//默认动作组的当前动作
    private String default_block_current_action = "";//中断组的当前动作

    private boolean is_current_block_state = false;

    public static final int ACTION_TYPE_DEFAULT = 1;//动作组，默认指令，三个动作
    public static final int ACTION_TYPE_DEFAULT_GET_HUNGER_1 = 2;//动作组，默认指令，饥饿趴着
    public static final int ACTION_TYPE_DEFAULT_SIT_DOWN = 3;//动作组，默认指令，坐着
    public static final int ACTION_TYPE_BLOCK_EAT_1_1 = 4;//动作组，中断指令，吃饭
    public static final int ACTION_TYPE_BLOCK_SIT_DOWN = 5;//动作组，中断指令，坐下
    public static final int ACTION_TYPE_BLOCK_GET_DOWN = 6;//动作组，中断指令，趴下
    public static final int ACTION_TYPE_BLOCK_BARK = 7;//动作组，中断指令，趴下
    public static final int ACTION_TYPE_BLOCK_SMILE = 8;//动作组，中断指令，微笑
    //TODO ...
    private PetOnClick pet_click;

    public LottieAnimView2(Context context) {
        super(context);
        init(context);
    }

    public LottieAnimView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LottieAnimView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        command_handler = new Handler(this);
//        if (getPetInfo(context).getPet_id() == 1) {//dog
            walk = dog_level_1_walk;
            sit_down = dog_level_1_sit_down;//坐下
            keep_sit_down = dog_level_1_keep_sit_down;//一直坐着
            angry = dog_level_1_angry;//生气
            smile = dog_level_1_smile;//微笑
            hunger_1 = dog_level_1_hunger_1;//饥饿，扑倒
            hunger_2 = dog_level_1_hunger_2;//饥饿，倒下循环
            eat_1_1 = dog_level_1_eat_1_1;//吃饭，开始
            eat_1_2 = dog_level_1_eat_1_2;//吃饭，开心摇头
            eat_1_dark = dog_level_1_eat_1_dark;//吃饭，吃完叫和单独叫共用
            get_down = dog_level_1_get_down;
            setAnimation("dog_level_1.json", CacheStrategy.None);
            setImageAssetsFolder("dog_level_1/");
//        } else if (getPetInfo(context).getPet_id() == 2) {//cat
//            walk = cat_level_1_walk;
//            sit_down = cat_level_1_sit_down;//坐下
//            keep_sit_down = cat_level_1_keep_sit_down;//一直坐着
//            angry = cat_level_1_angry;//生气
//            smile = cat_level_1_smile;//微笑
//            hunger_1 = cat_level_1_hunger_1;//饥饿，扑倒
//            eat_1_1 = cat_level_1_eat_1_1;//吃饭，开始
//            eat_1_2 = cat_level_1_eat_1_2;//吃饭，开心摇头
//            eat_1_dark = cat_level_1_eat_1_dark;//吃饭，吃完叫和单独叫共用
//            get_down = cat_level_1_get_down;
//            setAnimation("cat_level_1.json", CacheStrategy.None);
//            setImageAssetsFolder("cat_level_1/");
//        }
    }

//    private PetInfo getPetInfo(Context context) {
//        if (petInfo == null) {
//            petInfo = PetInfoDBHelper.getPetAdopted(context);
//        }
//        return petInfo;
//    }

    public interface BlockActionCallBack {
        void eatOver();
    }

    //中断指令动作
    public void intentBlockAction(int action_type, BlockActionCallBack blockActionCallBack) {
        this.blockActionCallBack = blockActionCallBack;
        switch (action_type) {
            case ACTION_TYPE_BLOCK_EAT_1_1:
                command_handler.sendEmptyMessage(HANDLER_COMMAND_1);
                break;
            case ACTION_TYPE_BLOCK_SIT_DOWN:
                command_handler.sendEmptyMessage(HANDLER_COMMAND_2);
                break;
            case ACTION_TYPE_BLOCK_GET_DOWN:
                command_handler.sendEmptyMessage(HANDLER_COMMAND_3);
                break;
            case ACTION_TYPE_BLOCK_BARK:
                command_handler.sendEmptyMessage(HANDLER_COMMAND_7);
                break;
            case ACTION_TYPE_BLOCK_SMILE:
                command_handler.sendEmptyMessage(HANDLER_COMMAND_8);
                break;

        }
    }

    //默认指令动作
    public void intentCurrentDefaultAction(int action_type) {
        switch (action_type) {
            case ACTION_TYPE_DEFAULT:
                command_handler.sendEmptyMessage(HANDLER_COMMAND_4);
                break;
            case ACTION_TYPE_DEFAULT_GET_HUNGER_1:
                command_handler.sendEmptyMessage(HANDLER_COMMAND_5);
                break;
            case ACTION_TYPE_DEFAULT_SIT_DOWN:
                command_handler.sendEmptyMessage(HANDLER_COMMAND_6);
                break;
        }
    }

    public void intentStop() {
        pauseAnimation();
    }

    //动作组，默认三个
    private synchronized void setCurrentDefaultAnimation(String[] strs) {
        is_current_block_state = false;
        String current_name = strs[0];
        float start_time = Float.valueOf(strs[1]);
        float end_time = Float.valueOf(strs[2]);
        long duration = Long.valueOf(strs[3]);
        Integer repeat_count = Integer.parseInt(strs[4]);
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
                    if (default_current_action.equals("walk")) {//走路来回一次
                        setCurrentDefaultAnimation(sit_down);
                    } else if (default_current_action.equals("sit_down")) {//坐着一次
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        setCurrentDefaultAnimation(get_down);
                    } else if (default_current_action.equals("get_down")) {//趴下一次
                        setCurrentDefaultAnimation(walk);
                    } else if (default_current_action.equals("hunger_1")) {//饥饿起始
                        setCurrentDefaultAnimation(hunger_2);
                    } else if (default_current_action.equals("hunger_2")) {//饥饿循环
                        setCurrentDefaultAnimation(hunger_2);
                    } else if (default_current_action.equals("hunger")) {//饥饿循环
                        setCurrentDefaultAnimation(hunger_1);
                    } else if (default_current_action.equals("keep_sit_down")) {//一直坐着
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        setCurrentDefaultAnimation(keep_sit_down);
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

    private synchronized void setBlockAnimation(String[] strs) {
        if (default_valueAnimator != null && default_valueAnimator.isRunning()) {
            default_valueAnimator.removeAllUpdateListeners();
            default_valueAnimator.removeAllListeners();
            default_valueAnimator.cancel();
        }
        String current_name = strs[0];
        if (current_name.equals("eat_1_1")) {
            default_block_current_action = "eat_1_1";
        } else if (current_name.equals("eat_1_2")) {
            default_block_current_action = "eat_1_2";
        }
        float start_time = Float.valueOf(strs[1]);
        float end_time = Float.valueOf(strs[2]);
        long duration = Long.valueOf(strs[3]);
        block_valueAnimator = ValueAnimator.ofFloat(start_time, end_time).setDuration(duration);
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
            if (blockActionCallBack != null) {
                blockActionCallBack.eatOver();//吃饭结束
            }
        } else if (default_block_current_action.equals("eat_1_2")) {
            default_block_current_action = "";//中断组，吃饭执行完毕，取消拦截
            defaultGroupEnd();//中断组，吃饭执行完毕，开始默认组
        } else {
            defaultGroupEnd();//中断组，坐下，趴下，微笑执行完毕，开始默认组
        }
    }

    private void defaultGroupEnd() {
        if (default_current_action.equals("walk")) {//默认组，优先级按循序排即可
            setCurrentDefaultAnimation(sit_down);
        } else if (default_current_action.equals("sit_down")) {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setCurrentDefaultAnimation(get_down);
        } else if (default_current_action.equals("get_down")) {
            setCurrentDefaultAnimation(walk);
        } else if (default_current_action.equals("hunger_1") || default_current_action.equals("hunger_2")) {
            setCurrentDefaultAnimation(hunger_2);
        } else if (default_current_action.equals("hunger")) {//饥饿循环
            setCurrentDefaultAnimation(hunger_1);
        }  else if (default_current_action.equals("keep_sit_down")) {//一直坐着
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setCurrentDefaultAnimation(keep_sit_down);
        } else {
            setCurrentDefaultAnimation(walk);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HANDLER_COMMAND_1:
                if (block_valueAnimator != null && block_valueAnimator.isRunning()) {//如果正在播放中断指令就不执行下一条指令
                } else {
                    setBlockAnimation(eat_1_1);
                }
                break;
            case HANDLER_COMMAND_2:
                if (block_valueAnimator != null && block_valueAnimator.isRunning()) {//如果正在播放中断指令就不执行下一条指令
                } else {
                    setBlockAnimation(sit_down);
                }
                break;
            case HANDLER_COMMAND_3:
                if (block_valueAnimator != null && block_valueAnimator.isRunning()) {//如果正在播放中断指令就不执行下一条指令
                } else {
                    setBlockAnimation(get_down);
                }
                break;
            case HANDLER_COMMAND_7:
                if (block_valueAnimator != null && block_valueAnimator.isRunning()) {//如果正在播放中断指令就不执行下一条指令
                } else {
                    setBlockAnimation(eat_1_dark);
                }
                break;
            case HANDLER_COMMAND_8:
                if (block_valueAnimator != null && block_valueAnimator.isRunning()) {//如果正在播放中断指令就不执行下一条指令
                } else {
                    setBlockAnimation(smile);
                }
                break;
            case HANDLER_COMMAND_4:
                setCurrentDefaultAnimation(walk);
                break;
            case HANDLER_COMMAND_5:
                setCurrentDefaultAnimation(hunger_1);
                break;
            case HANDLER_COMMAND_6:
                setCurrentDefaultAnimation(keep_sit_down);
                break;
        }
        return false;
    }

    private void petBodyEvent(ValueAnimator animation) {
        Float aaaa = (Float) animation.getAnimatedValue();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isClickBody(event)) {
            if (pet_click != null) {
                pet_click.onClick();
            }
        }
        return super.onTouchEvent(event);
    }

    public interface PetOnClick {
        void onClick();
    }

    public void setPetOnClick(PetOnClick pet_click) {
        this.pet_click = pet_click;
    }

    private boolean isClickBody(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (y > 80 && y < 180) {//宠物图像y轴区间
//            if ((x > 90 && x < 370) || (x > 600 && x < 880)) {
            return true;
//            } else {
//                return false;
//            }
        }
        return false;
    }

    //停止动画
    public void cancelLottieAnimation() {
        cancelAnimation();
        if (default_valueAnimator != null) {
            default_valueAnimator.cancel();
            default_valueAnimator = null;
        }
        if (block_valueAnimator != null) {
            block_valueAnimator.cancel();
            block_valueAnimator = null;
        }
    }
}