package com.view.kevin.myview.activity.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.view.kevin.myview.R;

public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    //刷屏主线程
    private Thread gameThread;
    //游戏屏幕宽和高
    public static int screen_width, screen_height;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder sfh;
    private boolean run_flag = false;
    //敌人图片
    private Bitmap bmp_enemy;
    //移动速度
    private int enemy_speed = 5;
    //角色移动控制帧
    private int frame = 0;
    //按键上下左右移动的判断
    private boolean is_up, is_down, is_left, is_right;
    //上下左右移动动画帧编号
    private int[] enemy_up = {3, 4, 5};
    private int[] enemy_down = {0, 1, 2};
    private int[] enemy_left = {6, 7, 8};
    private int[] enemy_right = {9, 10, 11};
    //当前帧动画
    private int[] enemy_start = enemy_down;
    //初始角色单帧图片右上角位置和单帧图片宽和高
    //右上角位置作为图片的人物的控制点不太合适
    //private int X=50,Y=50,W,H;

    //人物的控制点坐标和宽高
    private int X, Y, W, H;

    public GameView(Context context) {
        super(context);
        sfh = getHolder();
        sfh.addCallback(this);

        paint = new Paint();
        paint.setAntiAlias(true);
        //获得焦点
        setFocusable(true);
        setClickable(true);
        setFocusableInTouchMode(true);
        //保持屏幕常亮
        setKeepScreenOn(true);

        bmp_enemy = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        W = bmp_enemy.getWidth() / 13;
        H = bmp_enemy.getHeight();
        //这里我以单帧图片的中心点为控制点。具体控制点的位置，主要看你的图片了
        X = X + W / 2;
        Y = Y + Y / 2;
    }

    public void draw(Canvas canvas, Paint paint) {

		/*  第一种绘制方法                  */
        /*
        canvas.drawColor(Color.GRAY);
		//屏幕显示区域
		Rect rect_dst=new Rect(X-W/2, Y-H/2, X+W/2, Y+H/2);
		//图片截取区域
		Rect rect_src=new Rect(enemy_start[frame]*W, 0, (enemy_start[frame]+1)*W, H);
		canvas.drawBitmap(bmp_enemy,rect_src,rect_dst,paint);
		*/

		/*  第二种绘制方法                  */
        canvas.save();
        canvas.drawColor(Color.GRAY);
        //设置可视区域
        canvas.clipRect(X - W / 2, Y - H / 2, X + W / 2, Y + H / 2);
        canvas.drawBitmap(bmp_enemy, X - enemy_start[frame] * W - W / 2, Y - H / 2, paint);
        canvas.restore();
    }

    public void logic() {
        //根据按键状态判断
        if (is_up) {
            Y -= enemy_speed;
        } else if (is_down) {
            Y += enemy_speed;
        } else if (is_left) {
            X -= enemy_speed;
        } else if (is_right) {
            X += enemy_speed;
        }
        //边界暂时先不判断
        if (X < 0) {
            X = 0;
        }
        if (Y < 0) {
            Y = 0;
        }
        if (X > screen_width) {
            X = screen_width;
        }
        if (Y > screen_height) {
            Y = screen_height;
        }


        //移动动画帧的处理
        if ((is_up || is_down || is_left || is_right) || frame != 0) {
            if (frame < 2) {
                frame++;
            } else {
                frame = 0;
            }
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            is_up = true;
            enemy_start = enemy_up;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            is_down = true;
            enemy_start = enemy_down;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            is_left = true;
            enemy_start = enemy_left;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            is_right = true;
            enemy_start = enemy_right;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        is_up = false;
        is_down = false;
        is_left = false;
        is_right = false;
        return super.onKeyUp(keyCode, event);
    }


    @Override
    public void run() {
        while (run_flag) {
            try {
                long startTime = System.currentTimeMillis();
                canvas = sfh.lockCanvas();
                logic();
                draw(canvas, paint);
                long endTime = System.currentTimeMillis();
                long useTime = endTime - startTime;
                //固定屏幕刷新的时间为50ms
                if (useTime < 50) {
                    Thread.sleep(50 - useTime);
                }
            } catch (Exception e) {
                Log.e("Error", "刷屏线程出错了" + e);
            } finally {
                if (canvas != null) {
                    sfh.unlockCanvasAndPost(canvas);
                }
            }

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screen_width = getWidth();
        screen_height = getHeight();
        gameThread = new Thread(this);
        run_flag = true;
        //启动线程
        gameThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        run_flag = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

}
