package com.view.kevin.myview.activity.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by Kevin on 2017/3/28.
 * 适用于宠物主页面动效
 */

public class LottieAnimView extends LottieAnimationView implements Handler.Callback {
    private Handler command_handler;
    private BlockActionCallBack blockActionCallBack;//中断指令的回调
    private final int HANDLER_COMMAND_1 = 1;//中断指令吃
    private final int HANDLER_COMMAND_2 = 2;//中断指令坐下
    private final int HANDLER_COMMAND_3 = 3;//中断指令趴下
    private final int HANDLER_COMMAND_7 = 7;//中断指令叫
    private final int HANDLER_COMMAND_8 = 8;//中断指令笑
    private final int HANDLER_COMMAND_9 = 9;//中断指令洗澡
    private final int HANDLER_COMMAND_10 = 10;//中断指令玩球
    private final int HANDLER_COMMAND_4 = 4;//默认指令，走路(三个动作)
    private final int HANDLER_COMMAND_5 = 5;//默认指令，饿，睡觉
    private final int HANDLER_COMMAND_6 = 6;//默认指令，坐着

    private String[] walk = new String[5];//走路
    private String[] run = new String[5];//跑步
    private String[] sit_down = new String[5];//坐下
    private String[] keep_sit_down = new String[5];//一直坐着
    private String[] angry = new String[5];//生气
    private String[] smile = new String[5];//微笑
    private String[] hunger = new String[5];//饥饿，倒下循环
    private String[] sleep = new String[5];//睡觉，循环
    private String[] eat_1_1 = new String[5];//吃饭，开始
    private String[] eat_1_2 = new String[5];//吃饭，开心摇头
    private String[] eat_1_dark = new String[5];//吃饭，吃完叫和单独叫共用
    private String[] get_down = new String[5];//趴下
    private String[] shower = new String[5];//洗澡
    private String[] play_ball = new String[5];//玩球
    //狗一级
    private String[] dog_level_1_walk = new String[]{"walk", "0", "0.176285826", "17960", "0"};//走路
    private String[] dog_level_1_run = new String[]{"run", "0.758146839", "0.836670592", "8000", "0"};//跑
    private String[] dog_level_1_get_down = new String[]{"get_down", "0.583824106", "0.675304279", "9320", "0"};//趴下吐舌头
    private String[] dog_level_1_sit_down = new String[]{"sit_down", "0.210443659", "0.308598351", "10000", "0"};//坐下
    private String[] dog_level_1_keep_sit_down = new String[]{"keep_sit_down", "0.210443659", "0.308598351", "10000", "0"};//一直坐着
    private String[] dog_level_1_angry = new String[]{"angry", "0.176678445", "0.210051040", "3400", "0"};//生气
    private String[] dog_level_1_smile = new String[]{"smile", "0.308990969", "0.366313309", "5840", "0"};//微笑
    private String[] dog_level_1_hunger = new String[]{"hunger", "0.366705928", "0.419709462", "5400", "1"};//饥饿，趴下
    private String[] dog_level_1_eat_1_1 = new String[]{"eat_1_1", "0.420102080", "0.472320376", "5320", "0"};//吃饭，开始
    private String[] dog_level_1_eat_1_2 = new String[]{"eat_1_2", "0.472712995", "0.510797016", "3880", "0"};//吃饭，开心摇头
    private String[] dog_level_1_eat_1_dark = new String[]{"eat_1_dark", "0.563800549", "0.583431488", "2000", "0"};//吃饭，吃完叫和单独叫共用
    private String[] dog_level_1_sleep = new String[]{"sleep", "0.901845308", "1.0", "10000", "0"};//睡觉
    private String[] dog_level_1_shower = new String[]{"shower", "0.837063211", "0.901452689", "6560", "0"};//淋浴
    private String[] dog_level_1_play_ball = new String[]{"play_ball", "0.675696898", "0.757754220", "8360", "0"};//玩球
    //猫一级
    private String[] cat_level_1_walk = new String[]{"walk", "0", "0.176838810", "18080", "0"};//走路
    private String[] cat_level_1_run = new String[]{"run", "0.749217527", "0.827464788", "8000", "0"};//跑
    private String[] cat_level_1_get_down = new String[]{"get_down", "0.566901408", "0.664710485", "10000", "0"};//趴下吐舌头
    private String[] cat_level_1_sit_down = new String[]{"sit_down", "0.212050078", "0.312206572", "10240", "0"};//坐下
    private String[] cat_level_1_keep_sit_down = new String[]{"keep_sit_down", "0.212050078", "0.312206572", "10240", "0"};//一直坐着
    private String[] cat_level_1_angry = new String[]{"angry", "0.177230046", "0.211658841", "3520", "0"};//生气
    private String[] cat_level_1_smile = new String[]{"smile", "0.312597809", "0.371283255", "6000", "0"};//微笑
    private String[] cat_level_1_hunger = new String[]{"hunger", "0.371674491", "0.424491392", "5400", "1"};//饥饿，趴下
    private String[] cat_level_1_eat_1_1 = new String[]{"eat_1_1", "0.424882629", "0.477699530", "5400", "0"};//吃饭，开始
    private String[] cat_level_1_eat_1_2 = new String[]{"eat_1_2", "0.478090766", "0.536776212", "6000", "0"};//吃饭，开心摇头
    private String[] cat_level_1_eat_1_dark = new String[]{"eat_1_dark", "0.537167449", "0.566510172", "3000", "0"};//吃饭，吃完叫和单独叫共用
    private String[] cat_level_1_sleep = new String[]{"sleep", "0.902190923", "1.0", "10000", "0"};//睡觉
    private String[] cat_level_1_shower = new String[]{"shower", "0.827856025", "0.901799687", "7560", "0"};//淋浴
    private String[] cat_level_1_play_ball = new String[]{"play_ball", "0.665101721", "0.748826291", "8560", "0"};//玩球

    private ValueAnimator default_valueAnimator;
    private ValueAnimator block_valueAnimator;

    private String default_current_action = "";//默认动作组的当前动作
    private String default_block_current_action = "";//中断组的当前动作

    private boolean is_current_block_state = false;

    public static final int ACTION_TYPE_DEFAULT = 1;//动作组，默认指令，三个动作
    public static final int ACTION_TYPE_DEFAULT_GET_HUNGER_1 = 2;//动作组，默认指令，饥饿趴着睡觉
    public static final int ACTION_TYPE_DEFAULT_SIT_DOWN = 3;//动作组，默认指令，坐着
    public static final int ACTION_TYPE_BLOCK_EAT_1_1 = 4;//动作组，中断指令，吃饭
    public static final int ACTION_TYPE_BLOCK_SIT_DOWN = 5;//动作组，中断指令，坐下
    public static final int ACTION_TYPE_BLOCK_GET_DOWN = 6;//动作组，中断指令，趴下
    public static final int ACTION_TYPE_BLOCK_BARK = 7;//动作组，中断指令，叫
    public static final int ACTION_TYPE_BLOCK_SMILE = 8;//动作组，中断指令，微笑

    public static final int ACTION_TYPE_BLOCK_SHOWER = 9;//动作组，中断指令，洗澡
    public static final int ACTION_TYPE_BLOCK_PLAY_BALL = 10;//动作组，中断指令，玩球
    //TODO ...
    private PetOnClick pet_click;//宠物点击回调
    private boolean is_enable_onclick = true;//控件是否可以点击

    public LottieAnimView(Context context) {
        super(context);
        init(context);
    }

    public LottieAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LottieAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private int pet_type = 2;

    private void init(Context context) {
        command_handler = new Handler(this);
        if (pet_type == 1) {//dog
            walk = dog_level_1_walk;
            run = dog_level_1_run;
            sit_down = dog_level_1_sit_down;//坐下
            keep_sit_down = dog_level_1_keep_sit_down;//一直坐着
            angry = dog_level_1_angry;//生气
            smile = dog_level_1_smile;//微笑
            hunger = dog_level_1_hunger;//饥饿，扑倒
            sleep = dog_level_1_sleep;//睡觉
            eat_1_1 = dog_level_1_eat_1_1;//吃饭，开始
            eat_1_2 = dog_level_1_eat_1_2;//吃饭，开心摇头
            eat_1_dark = dog_level_1_eat_1_dark;//吃饭，吃完叫和单独叫共用
            get_down = dog_level_1_get_down;
            shower = dog_level_1_shower;
            play_ball = dog_level_1_play_ball;
            setAnimation("dog_level_1.json", CacheStrategy.Weak);
            setImageAssetsFolder("dog_level_1/");
        } else if (pet_type == 2) {//cat
            walk = cat_level_1_walk;
            run = cat_level_1_run;
            sit_down = cat_level_1_sit_down;//坐下
            keep_sit_down = cat_level_1_keep_sit_down;//一直坐着
            angry = cat_level_1_angry;//生气
            smile = cat_level_1_smile;//微笑
            hunger = cat_level_1_hunger;//饥饿
            sleep = cat_level_1_sleep;//睡觉
            eat_1_1 = cat_level_1_eat_1_1;//吃饭，开始
            eat_1_2 = cat_level_1_eat_1_2;//吃饭，开心摇头
            eat_1_dark = cat_level_1_eat_1_dark;//吃饭，吃完叫和单独叫共用
            get_down = cat_level_1_get_down;
            shower = cat_level_1_shower;
            play_ball = cat_level_1_play_ball;
            setAnimation("cat_level_1.json", CacheStrategy.Weak);
            setImageAssetsFolder("cat_level_1/");
        }
    }

    //动作指令，开始，结束
    public interface BlockActionCallBack {
        void startBlockAction();

        void overBlockAction();

        void eatOver1();
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
            case ACTION_TYPE_BLOCK_SHOWER:
                command_handler.sendEmptyMessage(HANDLER_COMMAND_9);
                break;
            case ACTION_TYPE_BLOCK_PLAY_BALL:
                command_handler.sendEmptyMessage(HANDLER_COMMAND_10);
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
        default_valueAnimator.setInterpolator(new LinearInterpolator());
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
                        setCurrentDefaultAnimation(run);
                    } else if (default_current_action.equals("run")) {//跑一次
                        setCurrentDefaultAnimation(get_down);
                    } else if (default_current_action.equals("get_down")) {//趴下一次
                        setCurrentDefaultAnimation(sit_down);
                    } else if (default_current_action.equals("sit_down")) {//坐着一次
                        setCurrentDefaultAnimation(eat_1_dark);
                    } else if (default_current_action.equals("eat_1_dark")) {//饥饿循环
                        setCurrentDefaultAnimation(walk);
                    } else if (default_current_action.equals("hunger")) {//饥饿
                        setCurrentDefaultAnimation(sleep);
                    } else if (default_current_action.equals("sleep")) {//睡觉
                        setCurrentDefaultAnimation(sleep);
                    } else if (default_current_action.equals("keep_sit_down")) {//一直坐着
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
        if (blockActionCallBack != null) {
            blockActionCallBack.startBlockAction();//中断指令开始回调
        }
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
        block_valueAnimator.setInterpolator(new LinearInterpolator());
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
                blockActionCallBack.eatOver1();//吃饭结束
            }
        } else if (default_block_current_action.equals("eat_1_2")) {
            default_block_current_action = "";//中断组，吃饭执行完毕，取消拦截
            defaultGroupEnd();//中断组，吃饭执行完毕，开始默认组
        } else {
            defaultGroupEnd();//中断组，坐下，趴下，微笑执行完毕，开始默认组
        }
    }

    private void defaultGroupEnd() {
        if (blockActionCallBack != null) {
            blockActionCallBack.overBlockAction();//中断指令结束回调
        }
        if (default_current_action.equals("walk")) {//走路来回一次
            setCurrentDefaultAnimation(run);
        } else if (default_current_action.equals("run")) {//跑一次
            setCurrentDefaultAnimation(get_down);
        } else if (default_current_action.equals("get_down")) {//趴下一次
            setCurrentDefaultAnimation(sit_down);
        } else if (default_current_action.equals("sit_down")) {//坐着一次
            setCurrentDefaultAnimation(eat_1_dark);
        } else if (default_current_action.equals("eat_1_dark")) {//饥饿循环
            setCurrentDefaultAnimation(walk);
        } else if (default_current_action.equals("hunger")) {//饥饿循环
            setCurrentDefaultAnimation(sleep);
        } else if (default_current_action.equals("sleep")) {//睡觉
            setCurrentDefaultAnimation(sleep);
        } else if (default_current_action.equals("keep_sit_down")) {//一直坐着
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
            case HANDLER_COMMAND_9:
                if (block_valueAnimator != null && block_valueAnimator.isRunning()) {//如果正在播放中断指令就不执行下一条指令
                } else {
                    setBlockAnimation(shower);
                }
                break;
            case HANDLER_COMMAND_10:
                if (block_valueAnimator != null && block_valueAnimator.isRunning()) {//如果正在播放中断指令就不执行下一条指令
                } else {
                    setBlockAnimation(play_ball);
                }
                break;
            case HANDLER_COMMAND_4:
                setCurrentDefaultAnimation(walk);
                break;
            case HANDLER_COMMAND_5:
                setCurrentDefaultAnimation(hunger);
                break;
            case HANDLER_COMMAND_6:
                setCurrentDefaultAnimation(keep_sit_down);
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isClickBody(event)) {
            if (pet_click != null && is_enable_onclick) {
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


    public void setViewTouchFalse() {
        is_enable_onclick = false;
    }

    public void setViewTouchTrue() {
        is_enable_onclick = true;
    }
}
