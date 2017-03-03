package com.view.kevin.myview.activity.view;

/**
 * Created by Administrator on 2017/3/3.
 */

import android.app.Activity;

import android.content.Context;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Color;

import android.graphics.Paint;

import android.graphics.Bitmap.Config;

import android.view.KeyEvent;

import android.view.View;

import com.view.kevin.myview.R;

public class MoveView2 extends View {

    public static final short DIRECTION_UP = 19;

    public static final short DIRECTION_DOWN = 20;

    public static final short DIRECTION_LEFT = 21;

    public static final short DIRECTION_RIGHT = 22;

    private int width;

    private int height;

    private Activity act = null;

    private Bitmap map = null;

    private Bitmap z[] = new Bitmap[2];

    private Bitmap rm[][] = new Bitmap[2][2];

    private Paint mPaint = new Paint();

    private Bitmap mzip = null;

    private int rx = 0, ry = 0;

    private int moveDirection = 0;        //移动方向

    private int dirction = DIRECTION_DOWN;    //目前面向

    private int move_p = 0;                //移动动画

    public MoveView2(Context context) {

        super(context);

        act = (Activity) context;

        width = act.getWindowManager().getDefaultDisplay().getWidth();

        height = act.getWindowManager().getDefaultDisplay().getHeight();

//        z[0] = new BitmapDrawable(this.getContext().getResources().openRawResource(R.drawable.setting)).getBitmap();
//        z[1] = new BitmapDrawable(this.getContext().getResources().openRawResource(R.drawable.ic_launcher)).getBitmap();
        z[0] = BitmapFactory.decodeResource(getResources(), R.drawable.setting);
        z[1] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

//        HandlerThread thread = new HandlerThread("chonghua");

//        thread.start();

//        Repaint r = new Repaint(thread.getLooper());

        new Thread(new Repaint()).start();

        createImage();

        setFocusable(true);  //设置控制焦点

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //键盘按下

        System.out.println("按键操作：" + keyCode);

        moveDirection = keyCode;

        dirction = keyCode;

        return super.onKeyDown(keyCode, event);

    }

    @Override

    public boolean onKeyUp(int keyCode, KeyEvent event) {

        moveDirection = 0;

        move_p = 0;

        return super.onKeyUp(keyCode, event);

    }

    /**
     * 创建一张地图。
     */

    public void createImage() {

        map = Bitmap.createBitmap(width, height, Config.ARGB_8888);        //创建于屏幕大小一致的图片

        Canvas canvas = new Canvas(map);        //获得画图对象

        canvas.drawColor(Color.WHITE);            //设置背景色为白色

        int r = height / 32;

        int c = width / 32;

        Paint p = new Paint();

        for (short i = 0; i < r; i++) {

            for (short f = 0; f < c; f++) {

                canvas.drawBitmap(z[0], f * 32, i * 32, p);

            }

        }

        mzip = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);   //获得人物

        //分解人物动作

        System.out.println("开始切图");

        for (short i = 0; i < 2; i++) {

            for (short f = 0; f < 2; f++) {

                rm[i][f] = Bitmap.createBitmap(mzip, f * 32, i * 48, 32, 48);

            }

        }

    }


    @Override

    protected void onDraw(Canvas canvas) {

        // TODO Auto-generated method stub

        super.onDraw(canvas);

        if (map != null) {

            canvas.drawBitmap(map, 0, 0, mPaint);

            short sd = 0;

            if (dirction == DIRECTION_UP) {

                sd = 3;

            } else if (dirction == DIRECTION_LEFT) {

                sd = 1;

            } else if (dirction == DIRECTION_RIGHT) {

                sd = 2;

            } else if (dirction == DIRECTION_DOWN) {

                sd = 0;

            }

            canvas.drawBitmap(rm[sd][move_p], rx, ry, mPaint);

        }

        System.out.println("据说调用到了onDraw函数");

    }


    /**
     * 移动小人。
     *
     * @param direction 方向
     */

    public void move(int direction) {

    }


    private class Repaint implements Runnable {

        public void run() {

            while (true) {

                if (moveDirection == DIRECTION_UP) {

                    ry -= 5;

                } else if (moveDirection == DIRECTION_DOWN) {

                    ry += 5;

                } else if (moveDirection == DIRECTION_LEFT) {

                    rx -= 5;

                } else if (moveDirection == DIRECTION_RIGHT) {

                    rx += 5;

                }

                System.out.println(rx + "-" + ry);

                if (moveDirection != 0) {

                    move_p++;

                    if (move_p == 4) {

                        move_p = 0;

                    }

                }

                //刷新

                postInvalidate();

                try {

                    Thread.sleep(50);

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }

        }

    }

}

